# Base de Datos

Documentación de la arquitectura y gestión de base de datos de Enchufate.

## Arquitectura

### Motor de Base de Datos

**PostgreSQL 14+**
- Servidor principal (Master)
- Servidor de replicación (Standby)
- Sincronización en tiempo real
- Backup automático diario

### Ubicación

- **Servidor Principal**: AWS RDS Multi-AZ
- **Respaldos**: AWS S3 (almacenamiento redundante)
- **Recuperación**: RTO 1 hora, RPO 15 minutos

## Tablas Principales

### Tabla: usuarios

```sql
CREATE TABLE usuarios (
    id UUID PRIMARY KEY,
    nombre_completo VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    password_hash VARCHAR(255) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_ultimo_login TIMESTAMP,
    tipo_usuario ENUM('cliente', 'admin', 'tecnico') DEFAULT 'cliente',
    estado ENUM('activo', 'inactivo', 'suspendido') DEFAULT 'activo',
    nro_documento VARCHAR(20),
    fecha_nacimiento DATE,
    pais VARCHAR(100),
    ciudad VARCHAR(100),
    foto_url TEXT,
    puntos INT DEFAULT 0,
    membresía_id UUID REFERENCES membresias(id)
);
```

### Tabla: reservas

```sql
CREATE TABLE reservas (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL REFERENCES usuarios(id),
    cubiculo_id INT NOT NULL,
    fecha_inicio TIMESTAMP NOT NULL,
    fecha_fin TIMESTAMP,
    estado ENUM('confirmada', 'en_curso', 'finalizada', 'cancelada'),
    tiempo_usado INT, -- minutos
    precio_total DECIMAL(10,2),
    pagado BOOLEAN DEFAULT FALSE,
    metodo_pago VARCHAR(50),
    notas TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Tabla: cubiculos

```sql
CREATE TABLE cubiculos (
    id INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    tipo ENUM('estandar', 'premium', 'empresarial') NOT NULL,
    estado ENUM('disponible', 'ocupado', 'mantenimiento') DEFAULT 'disponible',
    especificaciones JSONB,
    precio_por_hora DECIMAL(10,2),
    ultimo_mantenimiento TIMESTAMP,
    fecha_siguiente_mantenimiento TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Tabla: transacciones

```sql
CREATE TABLE transacciones (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL REFERENCES usuarios(id),
    tipo ENUM('pago', 'reembolso', 'ajuste') NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    metodo VARCHAR(50),
    referencia_externa VARCHAR(100),
    estado ENUM('completada', 'pendiente', 'fallida') DEFAULT 'pendiente',
    descripcion TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Tabla: membresías

```sql
CREATE TABLE membresias (
    id UUID PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo ENUM('estudiante', 'profesional', 'empresarial') NOT NULL,
    precio_mensual DECIMAL(10,2),
    caracteristicas JSONB,
    estado ENUM('activa', 'inactiva') DEFAULT 'activa',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Índices

```sql
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_usuarios_estado ON usuarios(estado);
CREATE INDEX idx_reservas_usuario ON reservas(usuario_id);
CREATE INDEX idx_reservas_cubiculo ON reservas(cubiculo_id);
CREATE INDEX idx_reservas_fecha ON reservas(fecha_inicio);
CREATE INDEX idx_transacciones_usuario ON transacciones(usuario_id);
CREATE INDEX idx_transacciones_fecha ON transacciones(created_at);
```

## Backups

### Estrategia de Backup

- **Tipo**: Full + Incremental
- **Frecuencia**: Diariamente (Full), cada 6 horas (Incremental)
- **Horario**: 02:00 AM UTC
- **Retención**: 30 días en S3, 90 días en Glacier
- **Verificación**: Restauración de prueba semanal

### Procedimiento de Backup

```bash
#!/bin/bash
# backup.sh - Ejecutar diariamente

DATE=$(date +%Y%m%d)
DB_NAME="enchufate_prod"
BACKUP_DIR="/backups"

# Full backup
pg_dump -h rds-endpoint.amazonaws.com \
    -U admin \
    -d $DB_NAME \
    > $BACKUP_DIR/full_$DATE.sql

# Comprimir
gzip $BACKUP_DIR/full_$DATE.sql

# Subir a S3
aws s3 cp $BACKUP_DIR/full_$DATE.sql.gz \
    s3://enchufate-backups/$DATE/
```

## Recuperación de Datos

### Restauración Completa

```bash
# Restaurar desde archivo
psql -h localhost \
    -U admin \
    -d enchufate_prod \
    < backup_2024.sql
```

### Recuperación Puntual (PITR)

```sql
-- Restaurar a un momento específico
SELECT pg_walfile_name_offset(pg_stop_backup());
-- Restaurar usando WAL
```

## Mantenimiento

### Diario

- Verificar status de replicación
- Revisar logs de errores
- Comprobar espacio en disco
- Monitorear conexiones activas

### Semanal

- VACUUM ANALYZE (mantenimiento)
- Revisar tamaño de tablas
- Comprobar índices no usados
- Verificación de integridad

### Mensual

- Análisis de rendimiento
- Revisión de queries lentas
- Optimización de índices
- Limpieza de logs antiguos

## Queries Útiles

### Top 10 Usuarios por Uso

```sql
SELECT 
    u.nombre_completo,
    COUNT(r.id) as num_reservas,
    SUM(r.tiempo_usado) as minutos_totales,
    ROUND(SUM(r.tiempo_usado)::numeric / 60, 2) as horas_totales
FROM usuarios u
LEFT JOIN reservas r ON u.id = r.usuario_id
GROUP BY u.id, u.nombre_completo
ORDER BY horas_totales DESC
LIMIT 10;
```

### Ingresos Diarios

```sql
SELECT 
    DATE(fecha_inicio) as fecha,
    COUNT(*) as num_sesiones,
    SUM(precio_total) as ingresos_totales
FROM reservas
WHERE estado = 'finalizada'
GROUP BY DATE(fecha_inicio)
ORDER BY fecha DESC
LIMIT 30;
```

### Cubículos Disponibles Ahora

```sql
SELECT 
    id,
    nombre,
    tipo,
    estado
FROM cubiculos
WHERE estado = 'disponible'
ORDER BY tipo ASC;
```

## Seguridad de BD

### Control de Acceso

- **Admin**: Usuario: `admin_user`
- **API**: Usuario: `api_user` (permisos limitados)
- **Lectura**: Usuario: `read_only` (SELECT solamente)
- **Replicación**: Usuario: `replicator` (replicación)

### Encriptación

- SSL/TLS para conexiones
- Contraseñas hasheadas (bcrypt)
- Campos sensibles encriptados

### Auditoría

```sql
-- Habilitar auditoría
CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY,
    tabla VARCHAR(50),
    operacion VARCHAR(10),
    usuario VARCHAR(50),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    datos_antiguos JSONB,
    datos_nuevos JSONB
);
```

---

**Contactos**:
- DBA Primario: [Nombre] - [Email]
- DBA Secundario: [Nombre] - [Email]
- AWS Account Manager: [Email]

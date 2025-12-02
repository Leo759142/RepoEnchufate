# Manual del Sistema - Descripción General

Bienvenido al Manual del Sistema de Enchufate. Este documento proporciona documentación técnica para administradores, técnicos de TI y personal de operaciones.

## Descripción del Sistema

Enchufate es un sistema integrado de gestión de cibercafé que administra:

- **Reservas de cubículos**: Sistema online y presencial
- **Gestión de usuarios**: Registro, perfiles y autenticación
- **Facturación**: Control de pagos y comprobantes
- **Hardware**: Gestión de computadoras y periféricos
- **Red**: Monitoreo de conectividad e internet
- **Seguridad**: Acceso, vigilancia y protección de datos
- **Reportes**: Análisis de uso y estadísticas

## Arquit adura del Sistema

```
┌─────────────────────────────────────────────────────────┐
│                    Interfaz Web (Vue.js)                │
│                www.enchufate.com                        │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────┴──────────────────────────────────┐
│             API Backend (Node.js/Express)              │
│         - Autenticación                                 │
│         - Reservas y Pagos                              │
│         - Usuarios y Facturas                           │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────┴──────────────────────────────────┐
│            Base de Datos (PostgreSQL)                  │
│         - Usuarios                                      │
│         - Reservas                                      │
│         - Transacciones                                 │
│         - Configuración                                 │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────┴──────────────────────────────────┐
│        Sistema de Control Local (Kiosk Manager)        │
│         - Asignación de Cubículos                       │
│         - Monitoreo de Computadoras                     │
│         - Limpieza de Sesiones                          │
└─────────────────────────────────────────────────────────┘
```

## Componentes Principales

### 1. Frontend - Sitio Web
- **Framework**: Vue.js 3
- **UI**: Vuetify 3
- **Host**: Netlify / Vercel
- **CDN**: Cloudflare

### 2. Backend - API
- **Runtime**: Node.js 18+
- **Framework**: Express.js
- **Host**: AWS EC2 / DigitalOcean
- **Escalabilidad**: Docker + Kubernetes

### 3. Base de Datos
- **Motor**: PostgreSQL 14+
- **Backup**: Automatizado diariamente
- **Replicación**: Hot Standby
- **Respaldos**: AWS S3

### 4. Sistema Local
- **Kiosk PC**: Windows 11 Pro
- **Software**: Manager personalizado
- **Sincronización**: Real-time con API
- **Almacenamiento**: Caché local

### 5. Hardware de Cubículos
- **Tipo**: Estándar, Premium, Empresarial
- **OS**: Windows 11 Pro / Linux
- **Monitores**: Full HD / 2K / 4K
- **Red**: Gigabit Ethernet + WiFi 6

## Flujo de Datos

1. **Reserva Online**
   - Usuario → Web → API → BD
   - Confirmación → Email/SMS

2. **Registro en Local**
   - Recepción → Kiosk → API → BD
   - Credentials generadas localmente

3. **Sesión en Cubículo**
   - Login → Kiosk → Sistema Local
   - Timer → Monitoreo → Cierre automático

4. **Cierre de Sesión**
   - Script de limpieza
   - Reporte → API → BD
   - Facturación automática

## Seguridad

- **SSL/TLS**: Encriptación end-to-end
- **Autenticación**: OAuth 2.0 + 2FA
- **Autorización**: Role-based access control
- **Auditoría**: Registro de todas las acciones
- **Cumplimiento**: GDPR, ISO 27001, PCI DSS

## Mantenimiento

- **Backups**: Diarios a las 2:00 AM
- **Actualizaciones**: Martes a las 3:00 AM
- **Monitoreo**: 24/7 con alertas
- **Soporte**: Escalada en 1 hora

---

Para más detalles, consulta las secciones específicas:
- [Configuración de Hardware](./hardware)
- [Administración de Red](./red)
- [Base de Datos](./base-datos)
- [Operaciones Diarias](./operaciones)

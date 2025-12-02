# Configuración de Hardware

Guía de configuración y mantenimiento del hardware de Enchufate.

## Especificaciones por Tipo de Cubículo

### Cubículo Estándar

| Componente | Especificación |
|-----------|-----------------|
| CPU | Intel i5-12400 / AMD Ryzen 5 5600G |
| RAM | 16GB DDR4 3200MHz |
| Almacenamiento | SSD 512GB NVMe |
| GPU | Integrada (Intel UHD 730 / AMD Radeon) |
| Monitor | 24" Full HD (1920x1080) IPS |
| Periféricos | Teclado USB + Mouse óptico |
| Audio | Audífonos básicos con micrófono |
| Conectividad | Gigabit Ethernet + WiFi 6 |
| Fuente | 500W 80+ Bronze |
| Chasis | Mini ITX |

### Cubículo Premium

| Componente | Especificación |
|-----------|-----------------|
| CPU | Intel i7-12700K / AMD Ryzen 7 5700X |
| RAM | 32GB DDR4 3600MHz |
| Almacenamiento | SSD 1TB NVMe |
| GPU | RTX 3060 Ti / RTX 4070 |
| Monitores | 27" 2K (2560x1440) + 21" FullHD |
| Periféricos | Teclado mecánico Cherry MX + Mouse gaming |
| Audio | Audífonos gaming con micrófono |
| Conectividad | Gigabit Ethernet + WiFi 6E |
| Fuente | 750W 80+ Gold |
| Chasis | ATX medio |

### Cubículo Empresarial

| Componente | Especificación |
|-----------|-----------------|
| CPU | Intel Xeon W5-2495X / AMD Ryzen Threadripper |
| RAM | 64GB DDR5 5000MHz |
| Almacenamiento | 2x SSD 2TB NVMe RAID 0 |
| GPU | RTX 4090 / RTX 6000 Ada |
| Monitores | Dual 4K 27" (3840x2160) + 21" FullHD |
| Periféricos | Teclado / Mouse profesionales |
| Audio | Micrófono condenser USB + monitores referencia |
| Conectividad | Dual Gigabit Ethernet + WiFi 6E |
| Red Especial | VLAN dedicada |
| Fuente | 1200W 80+ Platinum |
| Chasis | Full Tower |
| Extras | Impresora 3D de escritorio |

## Instalación de Computadoras

### Paso 1: Preparación del Sitio

1. Preparar estación de trabajo (escritorio, silla, toma corriente)
2. Instalar divisiones/privacidad
3. Conectar cableado eléctrico
4. Instalar cableado de red (Ethernet)
5. Instalar aire acondicionado independiente

### Paso 2: Instalación de Hardware

1. Montar todos los componentes
2. Instalar RAM y SSD
3. Conectar monitores y periféricos
4. Verificar conexiones de red y corriente
5. Instalar iluminación LED

### Paso 3: Configuración de Software

1. Instalar Windows 11 Pro desde imagen personalizada
2. Activar Windows con licencia volumen
3. Instalar drivers de chipset y GPU
4. Instalar software antivirus y seguridad
5. Unir dominio corporativo (Active Directory)
6. Instalar software obligatorio (Office, Adobe, etc.)

### Paso 4: Pruebas

1. Test de hardware (memtest86, chkdsk)
2. Test de conectividad de red
3. Test de velocidad de internet (speedtest)
4. Verificar periféricos
5. Verificar software instalado

### Paso 5: Deployment

1. Instalar etiqueta de identificación
2. Registrar en inventario del sistema
3. Configurar monitoreo remoto
4. Realizar backup de imagen de sistema
5. Cubículo listo para uso

## Mantenimiento Preventivo

### Diario

- Limpiar teclado y mouse
- Verificar funcionamiento de monitores
- Revisar conexiones físicas
- Comprobar temperatura de CPUs

### Semanal

- Limpiar interiores del PC (aire comprimido)
- Actualizar Windows Defender
- Revisar logs de eventos
- Escaneo antivirus completo

### Mensual

- Limpieza completa del equipo (componentes internos)
- Reinicio de Windows (reparación de registro)
- Backup de configuración
- Test de velocidad de disco
- Revisión de actualizaciones de BIOS

### Trimestral

- Cambio de filtros de aire acondicionado
- Limpieza profunda del chasis
- Verificación de voltaje de fuente
- Reemplazo de pasta térmica (si necesario)
- Test de estrés (Prime95, FurMark)

### Anual

- Mantenimiento preventivo profesional
- Reemplazo de batería CMOS
- Inspección completa de componentes
- Actualización de drivers críticos
- Revisión de garantía de hardware

## Troubleshooting Común

### Computadora no enciende

1. Verificar conexión de corriente
2. Revisar botón de encendido
3. Verificar LED de power en motherboard
4. Revisar voltaje de fuente con multímetro
5. **Solución**: Posible falla de fuente - reemplazar

### No hay conexión de internet

1. Verificar cable Ethernet conectado
2. Verificar LED de red en NIC
3. Ping a gateway (ipconfig)
4. Verificar configuración DNS
5. Reiniciar switch de red
6. **Solución**: Posible falla de NIC - reemplazar cable/puerto

### Bajo rendimiento

1. Verificar uso de CPU/RAM en Task Manager
2. Ejecutar antivirus en modo seguro
3. Verificar temperatura con HWMonitor
4. Limpiar archivos temporales
5. Desfragmentar SSD (TRIM)
6. **Solución**: Posible malware - limpiar o reinstalar

### Monitor sin imagen

1. Verificar conexión del monitor
2. Probar con otro monitor
3. Reiniciar PC
4. Entrar en BIOS y verificar salida de vídeo
5. **Solución**: Posible falla de GPU o puerto - reemplazar

## Reemplazo de Componentes

### Herramientas Necesarias

- Destornilladores (Phillips, Torx)
- Pulsera antiestática
- Pasta térmica
- Limpiador isopropílico
- Aire comprimido

### Procedimiento Estándar

1. Apagar PC completamente
2. Desconectar cable de corriente
3. Poner pulsera antiestática
4. Abrir chasis
5. Localizar componente a reemplazar
6. Remover componente antiguo (fotografiar conexiones)
7. Instalar nuevo componente
8. Verificar todas las conexiones
9. Cerrar chasis
10. Conectar corriente y encender
11. Verificar detección de hardware
12. Ejecutar pruebas de diagnóstico

## Inventario de Hardware

El sistema mantiene registro de:
- Número de serie de cada componente
- Fecha de instalación
- Mantenimiento realizado
- Reemplazos efectuados
- Estado actual (Funcional/Mantenimiento/Reparación)
- Fecha de próximo mantenimiento

**Actualizar inventario** después de cualquier cambio de hardware.

## Garantía

- **Computadoras completas**: 3 años on-site
- **Componentes individuales**: 1-3 años según tipo
- **SSD**: 5 años con DRAM cachéNORTE

Registrar todas las incidencias bajo garantía en el sistema.

---

**Contacto de Soporte Técnico**:
- Email: soporte@enchufate.com
- Teléfono: +[Tu número]
- WhatsApp: [Tu número]

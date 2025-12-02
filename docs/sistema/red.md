# Administración de Red

Configuración y mantenimiento de la red de Enchufate.

## Infraestructura de Red

### Topología

```
                    ┌─────────────────────────┐
                    │   ISP (Fibra 500Mbps)   │
                    └────────────┬─────────────┘
                                 │
                    ┌────────────┴──────────────┐
                    │                           │
            ┌───────▼────────┐         ┌───────▼──────┐
            │ Modem Fibra    │         │ Firewall     │
            │ Dual WAN       │         │ Fortinet 3100│
            └───────┬────────┘         └───────┬──────┘
                    └────────────┬──────────────┘
                                 │
                    ┌────────────▼──────────────┐
                    │  Switch Core (L3)        │
                    │  Cisco Catalyst 3850    │
                    └────────────┬──────────────┘
                                 │
        ┌────────────┬───────────┼───────────┬────────────┐
        │            │           │           │            │
    ┌───▼──┐  ┌──────▼──┐  ┌────▼──┐  ┌────▼──┐  ┌──────▼──┐
    │VLAN  │  │ VLAN    │  │VLAN   │  │VLAN   │  │ VLAN    │
    │User  │  │Premium  │  │Empresa│  │Mgmt   │  │Invitados│
    │(50)  │  │(51)     │  │(52)   │  │(99)   │  │(100)    │
    └───┬──┘  └──┬──────┘  └───┬───┘  └───┬───┘  └─────┬───┘
        │        │             │         │             │
    ┌───▼──────┐ │       ┌─────▼──┐   ┌─▼────┐   ┌────▼──┐
    │8 Cubículos│ │   ┌──▼──┐ ┌──▼──┐│Access││   │Área   │
    │Estándar  │ │   │6 Cub││3 Cub││Pts   │   │Común  │
    └──────────┘ │   │Prem.││Emp.  │└──────┘   └───────┘
                └─┘   └─────┘ └─────┘
```

## VLANs

| VLAN | ID | Nombre | Cubículos | Características |
|------|-----|--------|-----------|-----------------|
| VLAN 50 | 50 | User | 1-8 | 100 Mbps dedicados |
| VLAN 51 | 51 | Premium | 9-14 | 200 Mbps dedicados |
| VLAN 52 | 52 | Empresa | 15-17 | 500 Mbps dedicados + VLAN exclusiva |
| VLAN 99 | 99 | Mgmt | Servers | Administración remota |
| VLAN 100 | 100 | Guest | WiFi Público | Internet abierto limitado |

## Configuración de WiFi

### Red Pública (WiFi Abierto)

- **SSID**: Enchufate-Gratis
- **Banda**: 2.4 GHz + 5 GHz
- **Seguridad**: Open (sin contraseña)
- **Velocidad**: Limitada a 10 Mbps por dispositivo
- **Límite de datos**: 2GB/mes por dispositivo
- **Portal cautivo**: Aceptar términos para usar

### Red Premium (WiFi Privada)

- **SSID**: Enchufate-Premium
- **Banda**: 2.4 GHz + 5 GHz
- **Seguridad**: WPA3 Enterprise
- **Velocidad**: Sin límite (según disponibilidad)
- **Autenticación**: Credenciales de membresía
- **Acceso**: Miembros premium + empresarial

### Red de Administración

- **SSID**: Enchufate-Admin (oculta)
- **Seguridad**: WPA3 Enterprise con certificado
- **Acceso**: Personal técnico solo
- **Aislamiento**: Red separada del resto

## Firewall y Seguridad

### Configuración de Firewall

**Reglas de Entrada**:
- HTTP (80): Permitido
- HTTPS (443): Permitido
- SSH (22): Solo desde IP del admin
- RDP (3389): Bloqueado de internet
- SNMP (161): Solo desde Nagios

**Reglas de Salida**:
- DNS (53): Permitido a servidores confiables
- HTTP/HTTPS: Permitido con restricciones por horario
- FTP/SFTP: Bloqueado (prevenir descarga de contenido)
- Torrents: Bloqueado (P2P)
- Proxy corporativo: Obligatorio para ciertos sitios

**Filtrado de Contenido**:
- Pornografía: Bloqueada
- Violencia explícita: Bloqueada
- Sitios de apuestas: Bloqueada para menores
- Piratería: Bloqueada
- Phishing/Malware: Bloqueada por antivirus

## DNS

### Servidores DNS Primarios

- **Primary**: 8.8.8.8 (Google)
- **Secondary**: 1.1.1.1 (Cloudflare)
- **Fallback**: 208.67.222.123 (OpenDNS)

### Resolución de Dominio

Todos los equipos usan DNS dinámico que apunta a:
- `api.enchufate.local` → API interna
- `portal.enchufate.local` → Portal de reservas
- `admin.enchufate.local` → Panel administrativo

## Monitoreo de Red

### Herramientas Implementadas

1. **Nagios** - Monitoreo de servicios
   - Alertas por email
   - Dashboard web
   - Histórico de eventos

2. **Wireshark** - Análisis de tráfico
   - Diagnóstico de problemas
   - Análisis de seguridad
   - Captura de paquetes

3. **NetFlow** - Análisis de flujo
   - Tráfico por usuario
   - Tráfico por protocolo
   - Detección de anomalías

### Umbral de Alertas

- Pérdida de paquetes > 5%: Alerta
- Latencia > 100ms: Alerta
- Uso de ancho de banda > 80%: Alerta
- Conexión caída: Alerta inmediata

## Mantenimiento de Red

### Diario

- Revisar logs de firewall
- Verificar status de conectividad
- Monitorear uso de ancho de banda
- Comprobar temperatura de switches

### Semanal

- Análisis de tráfico con Wireshark
- Revisión de intentos de intrusión
- Actualización de reglas firewall
- Backup de configuración de dispositivos

### Mensual

- Auditoría de seguridad de red
- Optimización de VLAN
- Test de velocidad de internet
- Revisión de QoS

### Trimestral

- Actualización de firmware de switches
- Auditoría de acceso de usuarios
- Test de failover de WAN
- Revisión de políticas de seguridad

## Troubleshooting de Red

### Sin internet en cubículos

1. Verificar LED en switch
2. Ping a gateway desde cliente
3. ipconfig /all para ver DHCP
4. Revisar logs de DHCP
5. Reiniciar switch POE
6. **Solución**: Posible falla de puerto - cambiar a puerto diferente

### Lento internet

1. Ejecutar speedtest en cubículo
2. Verificar QoS activo
3. Ver uso de ancho de banda total
4. Revisar si hay límite de velocidad
5. Comprobar procesamiento en firewall
6. **Solución**: Aumentar QoS o segmentar tráfico

### Conexión intermitente

1. Ver logs de reconexión del switch
2. Verificar cable de red (reemplazar)
3. Revisar configuración de DHCP
4. Comprobar interferencia WiFi
5. Test de continuidad del cable
6. **Solución**: Cable dañado o puerto defectuoso - reemplazar

## Documentación de Red

Mantener actualizado:

- [ ] Diagrama de red (Visio)
- [ ] Lista de IPs asignadas
- [ ] Configuración de switches
- [ ] Políticas de firewall
- [ ] Contraseñas (en gestor seguro)
- [ ] Documentación de cambios
- [ ] Contactos de ISP
- [ ] SLA de servicio

---

**Contacto ISP:**
- Proveedor: [Nombre]
- Número de línea: [Número]
- Soporte: [Teléfono]

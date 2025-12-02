# Manual de Usuario - Enchufate Cibercafé

Manual de usuario oficial para Enchufate Cibercafé, construido con VitePress.

## Instalación

```bash
# Instalar dependencias
npm install

# Iniciar servidor de desarrollo
npm run docs:dev

# Construir para producción
npm run docs:build

# Vista previa de la construcción
npm run docs:preview
```

## Estructura del Proyecto

```
docs/
├── .vitepress/
│   └── config.js          # Configuración de VitePress
├── guia/
│   ├── introduccion.md    # Bienvenida al cibercafé
│   ├── instalacion.md     # Registro e inicio de sesión
│   ├── configuracion.md   # Reserva de cubículos
│   └── uso-basico.md      # Servicios y tarifas
├── referencia/
│   ├── faq.md            # Preguntas frecuentes
│   └── troubleshooting.md # Normas y reglamento
└── index.md              # Página de inicio
```

## Desarrollo

El sitio estará disponible en `http://localhost:5173` después de ejecutar `npm run docs:dev`.

## Contenido

Este manual incluye información completa sobre:

- 💻 **Tipos de cubículos** y equipamiento
- 📅 **Sistema de reservas** online y presencial
- 💰 **Tarifas y servicios** (uso de computadoras, impresión, diseño)
- 🎯 **Membresías** (Estudiante, Profesional, Empresarial)
- 📋 **Normas de uso** y reglamento interno
- ❓ **Preguntas frecuentes** de clientes
- 🎓 **Descuentos especiales** para estudiantes

## Personalización

Edita `docs/.vitepress/config.js` para personalizar:
- Título y descripción del sitio
- Navegación principal
- Barra lateral
- Enlaces a redes sociales
- Footer

### Datos a actualizar

Antes de publicar, actualiza estos datos en los archivos:

1. **docs/guia/introduccion.md**:
   - Dirección del local
   - Teléfono de contacto
   - Email de contacto
   - WhatsApp

2. **docs/guia/uso-basico.md**:
   - Precios actualizados
   - Promociones vigentes

3. **docs/.vitepress/config.js**:
   - Enlaces a redes sociales
   - URL del sitio web

## Documentación de VitePress

Para más información sobre VitePress, visita: https://vitepress.dev

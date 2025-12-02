import { defineConfig } from 'vitepress'

export default defineConfig({
  title: "Enchufate - Manual General",
  description: "Manual de Usuario y Manual del Sistema",
  lang: 'es-ES',
  
  themeConfig: {
    nav: [
      { text: 'Inicio', link: '/' },
      { text: 'Manual Usuario', link: '/usuario/introduccion' },
      { text: 'Manual Sistema', link: '/sistema/descripcion' }
    ],

    sidebar: {
      '/usuario/': [
        {
          text: 'Manual de Usuario',
          items: [
            { text: 'Bienvenida', link: '/usuario/introduccion' },
            { text: 'Registro e Inicio de Sesión', link: '/usuario/login' },
            { text: 'Reserva de Cubículos', link: '/usuario/reservas' },
            { text: 'Servicios y Tarifas', link: '/usuario/tarifas' },
            { text: 'Seguridad y Privacidad', link: '/usuario/seguridad' },
            { text: 'Eventos y Promociones', link: '/usuario/eventos' }
          ]
        }
      ],
      '/sistema/': [
        {
          text: 'Manual del Sistema',
          items: [
            { text: 'Descripción General', link: '/sistema/descripcion' },
            { text: 'Configuración de Hardware', link: '/sistema/hardware' },
            { text: 'Administración de Red', link: '/sistema/red' },
            { text: 'Base de Datos', link: '/sistema/base-datos' },
            { text: 'Operaciones Diarias', link: '/sistema/operaciones' }
          ]
        }
      ],
      '/': [
        {
          text: 'Referencia',
          items: [
            { text: 'Preguntas Frecuentes', link: '/referencia/faq' },
            { text: 'Normas y Reglamento', link: '/referencia/troubleshooting' }
          ]
        }
      ]
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com' }
    ],

    footer: {
      message: 'Enchufate - Manual General del Cibercafé',
      copyright: 'Copyright © 2025'
    }
  }
})

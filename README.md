# SpeedFast 🚀

## Descripción de la Aplicación
**SpeedFast** es un sistema de gestión de logística y despachos enfocado en la administración ágil de envíos. La plataforma está diseñada para coordinar eficientemente a los **repartidores** y clasificar los requerimientos operativos en tres tipos principales: **Pedidos de Comida, Encomiendas y Pedidos Express**.

El proyecto ha sido desarrollado bajo los principios de la Programación Orientada a Objetos, implementando una arquitectura estructurada que separa la lógica del dominio (entidades, modelos principales y objetos de valor) de la persistencia de datos y utilidades. La carga de información inicial se realiza dinámicamente mediante la lectura de archivos de texto sin formato.

Objetivo de la actividad es trabajar con interfases comunes para las diferentes clases.
---

## Estructura del Proyecto

```text
├── resources/
│   ├── pedidos.txt
│   └── repartidores.txt
└── src/
    ├── app/
    │   └── SpeedFast.java
    ├── data/
    │   ├── enumerate/
    │   │   └── TipoServicio.java
    │   └── util/
    │       ├── ControladorEnvios.java
    │       ├── GestorInstancias.java
    │       └── LectorDatos.java
    ├── model/
    │   ├── core/
    │   │   ├── Pedido.java
    │   │   └── Persona.java
    │   ├── entities/
    │   │   ├── dealer/
    │   │   │   └── Repartidor.java
    │   │   └── order/
    │   │       ├── PedidoComida.java
    │   │       ├── PedidoEncomienda.java
    │   │       └── PedidoExpress.java
    │   ├── interfaces/
    │   │   ├── Asignable.java
    │   │   ├── Cancelable.java
    │   │   └── Historial.java
    │   └── valueobjects/
    └── ui/

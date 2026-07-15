# AutoCare

<p align="center">
  <img src="app/src/main/res/drawable/logo_autocare.png" alt="Logo AutoCare" width="200"/>
</p>

## Descripción

AutoCare es una aplicación móvil desarrollada para ayudar a los propietarios de vehículos a gestionar y controlar el mantenimiento de sus automóviles. La aplicación permite registrar vehículos, almacenar historiales de mantenimiento y llevar un control de gastos relacionados con el cuidado vehicular.

## Objetivo

Facilitar el seguimiento de mantenimientos preventivos y correctivos mediante una herramienta móvil sencilla, accesible y organizada.

## Tecnologías utilizadas

- Android Studio
- Java
- SQLite
- XML
- Git y GitHub

## Arquitectura

Patrón MVC (Modelo - Vista - Controlador)

## Estructura del proyecto

```text
```text
com.autocare
│
├── activities
│   ├── DashboardActivity.java
│   ├── DetalleVehiculoActivity.java
│   ├── EditarVehiculoActivity.java
│   ├── GastosActivity.java
│   ├── LoginActivity.java
│   ├── MantenimientoActivity.java
│   ├── RegisterActivity.java
│   ├── RegistrarMantenimientoActivity.java
│   └── VehiculoActivity.java
│
├── adapters
│   └── VehiculoAdapter.java
│
├── database
│   └── DatabaseHelper.java
│
├── models
│   ├── Usuario.java
│   ├── Vehiculo.java
│   ├── TipoMantenimiento.java
│   ├── Mantenimiento.java
│   ├── HistorialMantenimiento.java
│   └── Gasto.java
│
├── utils
│
└── MainActivity.java
```

## Funcionalidades principales

- Autenticación de usuarios mediante pantalla de inicio de sesión.

- Dashboard con indicadores generales:
    - Vehículos registrados.
    - Total de mantenimientos realizados.
    - Total invertido en mantenimiento.
    - Recordatorios del último mantenimiento de cada vehículo.

- Gestión completa de vehículos (CRUD):
    - Registrar vehículos.
    - Editar información.
    - Eliminar vehículos.
    - Visualizar listado completo.

- Gestión de mantenimientos:
    - Registrar nuevos mantenimientos.
    - Seleccionar el tipo de mantenimiento desde la base de datos.
    - Registrar kilometraje, fecha y descripción.
    - Historial de mantenimientos por vehículo.
    - Historial general de todos los mantenimientos.

- Gestión de gastos:
    - Registrar gastos asociados a un mantenimiento.
    - Consultar gastos individuales de cada mantenimiento.
    - Historial general de gastos de todos los vehículos.
    - Cálculo automático del total invertido.

- Sistema de recordatorios de mantenimiento mostrado en el Dashboard.

- Persistencia de datos utilizando SQLite mediante DatabaseHelper.

- Navegación entre actividades mediante Intents.
## Desarrollador

Emil Rodriguez

## Asignatura

Seminario de Proyecto II (ISW-411)

## Estado del proyecto

Prototipo funcional implementado (Fase de desarrollo de interfaces, navegación y lógica base de actividades).
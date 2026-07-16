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
com.autocare
│
├── activities
│   ├── DashboardActivity.java
│   ├── DetalleVehiculoActivity.java
│   ├── EditarVehiculoActivity.java
│   ├── GastosActivity.java
│   ├── LoginActivity.java
│   ├── RegistroUsuarioActivity.java
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

- Registro de usuarios.
- Inicio de sesión con validación de credenciales.
- Sesión persistente mediante SharedPreferences.
- Menú lateral (Navigation Drawer) para navegar entre los módulos principales.

- Dashboard con indicadores generales:
    - Vehículos registrados.
    - Total de mantenimientos realizados.
    - Total invertido.
    - Recordatorios del último mantenimiento.

- Gestión completa de vehículos (CRUD):
    - Registrar vehículos.
    - Editar información.
    - Eliminar vehículos.
    - Visualizar listado completo.
    - Consultar detalle del vehículo.

- Gestión de mantenimientos:
    - Registrar nuevos mantenimientos.
    - Seleccionar el tipo de mantenimiento.
    - Registrar fecha, kilometraje, descripción y costo.
    - Historial por vehículo.
    - Historial general.

- Gestión de gastos:
    - Registro automático de gastos asociados al mantenimiento.
    - Historial general de gastos.
    - Total invertido por vehículo.
    - Total invertido general.

- Persistencia de datos utilizando SQLite.

- Navegación entre actividades mediante Intents.

- Arquitectura organizada bajo el patrón MVC.

## Desarrollador

Emil Rodriguez

## Asignatura

Seminario de Proyecto II (ISW-411)

## Estado del proyecto

Versión 1.0 finalizada con las funcionalidades principales implementadas para fines académicos.
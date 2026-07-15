package com.autocare.models;

public class Mantenimiento {

    private int idMantenimiento;
    private int idVehiculo;
    private int idTipo;

    private String nombreTipo;

    private String fecha;
    private int kilometraje;
    private String descripcion;

    public Mantenimiento() {
    }

    public Mantenimiento(int idMantenimiento,
                         int idVehiculo,
                         int idTipo,
                         String nombreTipo,
                         String fecha,
                         int kilometraje,
                         String descripcion) {

        this.idMantenimiento = idMantenimiento;
        this.idVehiculo = idVehiculo;
        this.idTipo = idTipo;
        this.nombreTipo = nombreTipo;
        this.fecha = fecha;
        this.kilometraje = kilometraje;
        this.descripcion = descripcion;
    }

    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
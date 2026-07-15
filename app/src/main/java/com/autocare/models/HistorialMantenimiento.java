package com.autocare.models;

public class HistorialMantenimiento {

    private int idMantenimiento;
    private String tipo;
    private String fecha;
    private int kilometraje;
    private String descripcion;

    public HistorialMantenimiento() {
    }

    public HistorialMantenimiento(int idMantenimiento,
                                  String tipo,
                                  String fecha,
                                  int kilometraje,
                                  String descripcion) {

        this.idMantenimiento = idMantenimiento;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
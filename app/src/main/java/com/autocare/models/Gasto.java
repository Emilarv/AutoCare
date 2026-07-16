package com.autocare.models;

public class Gasto {

    private int idGasto;
    private int idMantenimiento;

    private double monto;
    private String categoria;

    private String vehiculo;
    private String mantenimiento;

    public Gasto() {
    }

    public Gasto(int idGasto,
                 int idMantenimiento,
                 double monto,
                 String categoria) {

        this.idGasto = idGasto;
        this.idMantenimiento = idMantenimiento;
        this.monto = monto;
        this.categoria = categoria;
    }

    public int getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(int idGasto) {
        this.idGasto = idGasto;
    }

    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(String mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

}
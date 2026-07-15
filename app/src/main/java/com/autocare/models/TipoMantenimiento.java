package com.autocare.models;

public class TipoMantenimiento {

    private int id;
    private String nombre;

    public TipoMantenimiento(int id, String nombre){

        this.id = id;
        this.nombre = nombre;

    }

    public int getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    @Override
    public String toString(){

        return nombre;

    }

}
package com.autocare.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "autocare.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE Usuario (" +
                        "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nombre TEXT NOT NULL," +
                        "correo TEXT UNIQUE NOT NULL," +
                        "contrasena TEXT NOT NULL," +
                        "fecha_registro TEXT," +
                        "estado TEXT" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE Vehiculo (" +
                        "id_vehiculo INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "id_usuario INTEGER NOT NULL," +
                        "marca TEXT NOT NULL," +
                        "modelo TEXT NOT NULL," +
                        "anio INTEGER," +
                        "placa TEXT UNIQUE," +
                        "color TEXT," +
                        "FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario)" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE TipoMantenimiento (" +
                        "id_tipo INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nombre_tipo TEXT NOT NULL," +
                        "descripcion TEXT," +
                        "intervalo_km INTEGER," +
                        "intervalo_meses INTEGER," +
                        "estado TEXT" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE Mantenimiento (" +
                        "id_mantenimiento INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "id_vehiculo INTEGER NOT NULL," +
                        "id_tipo INTEGER NOT NULL," +
                        "fecha TEXT NOT NULL," +
                        "kilometraje INTEGER," +
                        "descripcion TEXT," +
                        "FOREIGN KEY(id_vehiculo) REFERENCES Vehiculo(id_vehiculo)," +
                        "FOREIGN KEY(id_tipo) REFERENCES TipoMantenimiento(id_tipo)" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE Gasto (" +
                        "id_gasto INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "id_mantenimiento INTEGER NOT NULL," +
                        "monto REAL NOT NULL," +
                        "categoria TEXT," +
                        "FOREIGN KEY(id_mantenimiento) REFERENCES Mantenimiento(id_mantenimiento)" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Gasto");
        db.execSQL("DROP TABLE IF EXISTS Mantenimiento");
        db.execSQL("DROP TABLE IF EXISTS TipoMantenimiento");
        db.execSQL("DROP TABLE IF EXISTS Vehiculo");
        db.execSQL("DROP TABLE IF EXISTS Usuario");

        onCreate(db);
    }
}
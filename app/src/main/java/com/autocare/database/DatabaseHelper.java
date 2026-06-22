package com.autocare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.autocare.models.Vehiculo;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "autocare.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean insertarVehiculo(
            String marca,
            String modelo,
            int anio,
            String placa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id_usuario", 1);
        values.put("marca", marca);
        values.put("modelo", modelo);
        values.put("anio", anio);
        values.put("placa", placa);

        long resultado = db.insert(
                "Vehiculo",
                null,
                values
        );

        return resultado != -1;
    }

    // OBTENER TODOS LOS VEHICULOS
    public ArrayList<Vehiculo> obtenerVehiculos() {

        ArrayList<Vehiculo> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM Vehiculo",
                null
        );

        if (cursor.moveToFirst()) {

            do {

                Vehiculo vehiculo = new Vehiculo();

                vehiculo.setId(cursor.getInt(0));
                vehiculo.setMarca(cursor.getString(2));
                vehiculo.setModelo(cursor.getString(3));
                vehiculo.setAnio(cursor.getInt(4));
                vehiculo.setPlaca(cursor.getString(5));

                lista.add(vehiculo);

            } while (cursor.moveToNext());

        }

        cursor.close();

        return lista;
    }

    public Vehiculo obtenerVehiculoPorId(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM Vehiculo WHERE id_vehiculo=?",
                new String[]{String.valueOf(id)}
        );

        Vehiculo vehiculo = null;

        if (cursor.moveToFirst()) {

            vehiculo = new Vehiculo();

            vehiculo.setId(cursor.getInt(0));
            vehiculo.setMarca(cursor.getString(2));
            vehiculo.setModelo(cursor.getString(3));
            vehiculo.setAnio(cursor.getInt(4));
            vehiculo.setPlaca(cursor.getString(5));
        }

        cursor.close();

        return vehiculo;
    }

    public boolean actualizarVehiculo(
            int id,
            String marca,
            String modelo,
            int anio,
            String placa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("marca", marca);
        values.put("modelo", modelo);
        values.put("anio", anio);
        values.put("placa", placa);

        int filas = db.update(
                "Vehiculo",
                values,
                "id_vehiculo=?",
                new String[]{String.valueOf(id)}
        );

        return filas > 0;
    }

    public boolean eliminarVehiculo(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int filas = db.delete(
                "Vehiculo",
                "id_vehiculo=?",
                new String[]{String.valueOf(id)}
        );

        return filas > 0;
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
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Gasto");
        db.execSQL("DROP TABLE IF EXISTS Mantenimiento");
        db.execSQL("DROP TABLE IF EXISTS TipoMantenimiento");
        db.execSQL("DROP TABLE IF EXISTS Vehiculo");
        db.execSQL("DROP TABLE IF EXISTS Usuario");

        onCreate(db);
    }
}
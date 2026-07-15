package com.autocare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.autocare.models.Gasto;
import com.autocare.models.Usuario;

import com.autocare.models.Vehiculo;

import java.util.ArrayList;
import com.autocare.models.Mantenimiento;

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

        db.execSQL("INSERT INTO TipoMantenimiento (nombre_tipo, descripcion, intervalo_km, intervalo_meses, estado) VALUES ('Cambio de aceite','Cambio de aceite del motor',5000,6,'Activo');");

        db.execSQL("INSERT INTO TipoMantenimiento (nombre_tipo, descripcion, intervalo_km, intervalo_meses, estado) VALUES ('Cambio de filtro','Cambio del filtro de aceite',5000,6,'Activo');");

        db.execSQL("INSERT INTO TipoMantenimiento (nombre_tipo, descripcion, intervalo_km, intervalo_meses, estado) VALUES ('Cambio de filtro de aire','Cambio del filtro de aire del motor',10000,12,'Activo');");

        db.execSQL("INSERT INTO TipoMantenimiento (nombre_tipo, descripcion, intervalo_km, intervalo_meses, estado) VALUES ('Alineación','Alineación de las ruedas',10000,12,'Activo');");

        db.execSQL("INSERT INTO TipoMantenimiento (nombre_tipo, descripcion, intervalo_km, intervalo_meses, estado) VALUES ('Balanceo','Balanceo de neumáticos',10000,12,'Activo');");

        db.execSQL("INSERT INTO TipoMantenimiento (nombre_tipo, descripcion, intervalo_km, intervalo_meses, estado) VALUES ('Cambio de batería','Sustitución de la batería',40000,24,'Activo');");

        db.execSQL("INSERT INTO TipoMantenimiento (nombre_tipo, descripcion, intervalo_km, intervalo_meses, estado) VALUES ('Revisión de frenos','Inspección y mantenimiento del sistema de frenos',15000,12,'Activo');");

        db.execSQL("INSERT INTO TipoMantenimiento (nombre_tipo, descripcion, intervalo_km, intervalo_meses, estado) VALUES ('Cambio de refrigerante','Cambio del líquido refrigerante',40000,24,'Activo');");

        db.execSQL("INSERT INTO TipoMantenimiento (nombre_tipo, descripcion, intervalo_km, intervalo_meses, estado) VALUES ('Cambio de correa de distribución','Sustitución de la correa de distribución',60000,48,'Activo');");

        db.execSQL("INSERT INTO TipoMantenimiento (nombre_tipo, descripcion, intervalo_km, intervalo_meses, estado) VALUES ('Revisión general','Inspección completa del vehículo',10000,12,'Activo');");

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

    public long insertarMantenimiento(Mantenimiento mantenimiento) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id_vehiculo", mantenimiento.getIdVehiculo());
        values.put("id_tipo", mantenimiento.getIdTipo());
        values.put("fecha", mantenimiento.getFecha());
        values.put("kilometraje", mantenimiento.getKilometraje());
        values.put("descripcion", mantenimiento.getDescripcion());

        return db.insert(
                "Mantenimiento",
                null,
                values
        );

    }

    public ArrayList<Mantenimiento> obtenerMantenimientosPorVehiculo(int idVehiculo) {

        ArrayList<Mantenimiento> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM Mantenimiento WHERE id_vehiculo=?",
                new String[]{String.valueOf(idVehiculo)}
        );

        if (cursor.moveToFirst()) {

            do {

                Mantenimiento mantenimiento = new Mantenimiento();

                mantenimiento.setIdMantenimiento(cursor.getInt(0));
                mantenimiento.setIdVehiculo(cursor.getInt(1));
                mantenimiento.setIdTipo(cursor.getInt(2));
                mantenimiento.setFecha(cursor.getString(3));
                mantenimiento.setKilometraje(cursor.getInt(4));
                mantenimiento.setDescripcion(cursor.getString(5));

                lista.add(mantenimiento);

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return lista;

    }

    public ArrayList<Mantenimiento> obtenerHistorialMantenimiento(int idVehiculo) {

        ArrayList<Mantenimiento> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(

                "SELECT " +
                        "M.id_mantenimiento, " +
                        "T.nombre_tipo, " +
                        "M.fecha, " +
                        "M.kilometraje, " +
                        "M.descripcion " +
                        "FROM Mantenimiento M " +
                        "INNER JOIN TipoMantenimiento T " +
                        "ON M.id_tipo = T.id_tipo " +
                        "WHERE M.id_vehiculo = ? " +
                        "ORDER BY M.id_mantenimiento DESC",

                new String[]{String.valueOf(idVehiculo)}

        );

        if (cursor.moveToFirst()) {

            do {

                Mantenimiento mantenimiento = new Mantenimiento();

                mantenimiento.setIdMantenimiento(cursor.getInt(0));

                // Nombre del mantenimiento
                mantenimiento.setNombreTipo(cursor.getString(1));

                mantenimiento.setFecha(cursor.getString(2));

                mantenimiento.setKilometraje(cursor.getInt(3));

                mantenimiento.setDescripcion(cursor.getString(4));

                lista.add(mantenimiento);

            } while (cursor.moveToNext());

        }

        cursor.close();

        return lista;
    }

    public Cursor obtenerTiposMantenimiento() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT id_tipo, nombre_tipo FROM TipoMantenimiento ORDER BY nombre_tipo",
                null
        );

    }


    public boolean insertarGasto(Gasto gasto) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id_mantenimiento", gasto.getIdMantenimiento());
        values.put("monto", gasto.getMonto());
        values.put("categoria", gasto.getCategoria());

        long resultado = db.insert(
                "Gasto",
                null,
                values
        );

        return resultado != -1;
    }

    public ArrayList<Gasto> obtenerGastosPorMantenimiento(int idMantenimiento) {

        ArrayList<Gasto> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(

                "SELECT * FROM Gasto WHERE id_mantenimiento=? ORDER BY id_gasto DESC",

                new String[]{
                        String.valueOf(idMantenimiento)
                }

        );

        if (cursor.moveToFirst()) {

            do {

                Gasto gasto = new Gasto();

                gasto.setIdGasto(cursor.getInt(0));
                gasto.setIdMantenimiento(cursor.getInt(1));
                gasto.setMonto(cursor.getDouble(2));
                gasto.setCategoria(cursor.getString(3));

                lista.add(gasto);

            } while (cursor.moveToNext());

        }

        cursor.close();

        return lista;

    }

    public double obtenerTotalGastos(int idMantenimiento) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(

                "SELECT IFNULL(SUM(monto),0) FROM Gasto WHERE id_mantenimiento=?",

                new String[]{
                        String.valueOf(idMantenimiento)
                }

        );

        double total = 0;

        if (cursor.moveToFirst()) {

            total = cursor.getDouble(0);

        }

        cursor.close();

        return total;

    }

    public int obtenerCantidadMantenimientos() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM Mantenimiento",
                null
        );

        int cantidad = 0;

        if (cursor.moveToFirst()) {
            cantidad = cursor.getInt(0);
        }

        cursor.close();

        return cantidad;
    }

    public double obtenerTotalGastado() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT IFNULL(SUM(monto),0) FROM Gasto",
                null
        );

        double total = 0;

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }

        cursor.close();

        return total;
    }

    public int obtenerUltimoKilometrajeVehiculo(int idVehiculo) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(

                "SELECT IFNULL(MAX(kilometraje),0) FROM Mantenimiento WHERE id_vehiculo=?",

                new String[]{
                        String.valueOf(idVehiculo)
                }

        );

        int ultimoKm = 0;

        if (cursor.moveToFirst()) {
            ultimoKm = cursor.getInt(0);
        }

        cursor.close();

        return ultimoKm;
    }
    public String obtenerUltimaFechaMantenimiento() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(

                "SELECT fecha FROM Mantenimiento " +
                        "ORDER BY id_mantenimiento DESC LIMIT 1",

                null

        );

        String fecha = "--";

        if (cursor.moveToFirst()) {
            fecha = cursor.getString(0);
        }

        cursor.close();

        return fecha;
    }

    public double obtenerTotalGastadoVehiculo(int idVehiculo) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(

                "SELECT IFNULL(SUM(G.monto),0) " +
                        "FROM Gasto G " +
                        "INNER JOIN Mantenimiento M " +
                        "ON G.id_mantenimiento = M.id_mantenimiento " +
                        "WHERE M.id_vehiculo=?",

                new String[]{
                        String.valueOf(idVehiculo)
                }

        );

        double total = 0;

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }

        cursor.close();

        return total;
    }

    public Gasto obtenerGastoPorMantenimiento(int idMantenimiento) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(

                "SELECT * FROM Gasto WHERE id_mantenimiento=? LIMIT 1",

                new String[]{
                        String.valueOf(idMantenimiento)
                }

        );

        Gasto gasto = null;

        if (cursor.moveToFirst()) {

            gasto = new Gasto();

            gasto.setIdGasto(cursor.getInt(0));
            gasto.setIdMantenimiento(cursor.getInt(1));
            gasto.setMonto(cursor.getDouble(2));
            gasto.setCategoria(cursor.getString(3));

        }

        cursor.close();

        return gasto;
    }
    public ArrayList<Mantenimiento> obtenerTodosLosMantenimientos() {

        ArrayList<Mantenimiento> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(

                "SELECT " +
                        "M.id_mantenimiento, " +
                        "V.marca || ' ' || V.modelo, " +
                        "T.nombre_tipo, " +
                        "M.fecha, " +
                        "M.kilometraje, " +
                        "M.descripcion " +
                        "FROM Mantenimiento M " +
                        "INNER JOIN Vehiculo V ON M.id_vehiculo = V.id_vehiculo " +
                        "INNER JOIN TipoMantenimiento T ON M.id_tipo = T.id_tipo " +
                        "ORDER BY M.id_mantenimiento DESC",

                null

        );

        if (cursor.moveToFirst()) {

            do {

                Mantenimiento mantenimiento = new Mantenimiento();

                mantenimiento.setIdMantenimiento(cursor.getInt(0));

                // Aquí guardaremos el nombre del vehículo
                mantenimiento.setDescripcion(
                        cursor.getString(1)
                );

                mantenimiento.setNombreTipo(
                        cursor.getString(2)
                );

                mantenimiento.setFecha(
                        cursor.getString(3)
                );

                mantenimiento.setKilometraje(
                        cursor.getInt(4)
                );

                lista.add(mantenimiento);

            } while (cursor.moveToNext());

        }

        cursor.close();

        return lista;

    }
    public ArrayList<Gasto> obtenerTodosLosGastos() {

        ArrayList<Gasto> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(

                "SELECT * FROM Gasto ORDER BY id_gasto DESC",

                null

        );

        if (cursor.moveToFirst()) {

            do {

                Gasto gasto = new Gasto();

                gasto.setIdGasto(cursor.getInt(0));
                gasto.setIdMantenimiento(cursor.getInt(1));
                gasto.setMonto(cursor.getDouble(2));
                gasto.setCategoria(cursor.getString(3));

                lista.add(gasto);

            } while (cursor.moveToNext());

        }

        cursor.close();

        return lista;
    }
    public int obtenerCantidadVehiculos() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM Vehiculo",
                null
        );

        int cantidad = 0;

        if (cursor.moveToFirst()) {
            cantidad = cursor.getInt(0);
        }

        cursor.close();

        return cantidad;
    }
    public boolean existeCorreo(String correo) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(

                "SELECT id_usuario FROM Usuario WHERE correo=?",

                new String[]{correo}

        );

        boolean existe = cursor.moveToFirst();

        cursor.close();

        return existe;

    }

    public boolean registrarUsuario(
            String nombre,
            String correo,
            String contrasena,
            String fechaRegistro,
            String estado) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nombre", nombre);
        values.put("correo", correo);
        values.put("contrasena", contrasena);
        values.put("fecha_registro", fechaRegistro);
        values.put("estado", estado);

        long resultado = db.insert(
                "Usuario",
                null,
                values
        );

        return resultado != -1;

    }
    public Usuario iniciarSesion(
            String correo,
            String contrasena) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(

                "SELECT * FROM Usuario " +
                        "WHERE correo=? " +
                        "AND contrasena=? " +
                        "AND estado='Activo'",

                new String[]{
                        correo,
                        contrasena
                }

        );

        Usuario usuario = null;

        if (cursor.moveToFirst()) {

            usuario = new Usuario();

            usuario.setIdUsuario(cursor.getInt(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setCorreo(cursor.getString(2));
            usuario.setContrasena(cursor.getString(3));
            usuario.setFechaRegistro(cursor.getString(4));
            usuario.setEstado(cursor.getString(5));

        }

        cursor.close();

        return usuario;

    }
    public boolean existeAlgunUsuario() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(

                "SELECT COUNT(*) FROM Usuario",

                null

        );

        boolean existe = false;

        if(cursor.moveToFirst()){

            existe = cursor.getInt(0) > 0;

        }

        cursor.close();

        return existe;

    }
}


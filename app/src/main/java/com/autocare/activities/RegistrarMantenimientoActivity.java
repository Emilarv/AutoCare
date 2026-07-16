package com.autocare.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.R;
import com.autocare.database.DatabaseHelper;
import com.autocare.models.Mantenimiento;
import com.autocare.models.Gasto;
import com.autocare.models.TipoMantenimiento;

import java.util.ArrayList;
import java.util.Calendar;

public class RegistrarMantenimientoActivity extends AppCompatActivity {

    private TextView txtVehiculo;
    private TextView txtPlaca;

    private AutoCompleteTextView spTipo;
    private EditText txtFecha;
    private EditText txtKilometraje;
    private EditText txtCosto;
    private EditText txtDescripcion;
    private Button btnGuardar;

    private DatabaseHelper databaseHelper;
    private ArrayList<TipoMantenimiento> listaTipos;
    private int idVehiculo;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mantenimiento);

        txtVehiculo = findViewById(R.id.txtVehiculo);
        txtPlaca = findViewById(R.id.txtPlaca);
        spTipo = findViewById(R.id.spTipo);
        txtFecha = findViewById(R.id.txtFecha);
        txtKilometraje = findViewById(R.id.txtKilometraje);
        txtCosto = findViewById(R.id.txtCosto);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);

        databaseHelper = new DatabaseHelper(this);

        idVehiculo = getIntent().getIntExtra("idVehiculo", -1);
        String vehiculo = getIntent().getStringExtra("vehiculo");
        String placa = getIntent().getStringExtra("placa");

        txtVehiculo.setText(vehiculo);
        txtPlaca.setText("Placa: " + placa);

        spTipo.setInputType(InputType.TYPE_NULL);
        txtFecha.setInputType(InputType.TYPE_NULL);

        cargarTiposMantenimiento();

        spTipo.setOnClickListener(v -> spTipo.showDropDown());
        spTipo.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                spTipo.showDropDown();
            }
        });

        txtFecha.setOnClickListener(v -> mostrarCalendario());
        txtFecha.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mostrarCalendario();
            }
        });

        btnGuardar.setOnClickListener(v -> guardarMantenimiento());
    }

    private void cargarTiposMantenimiento() {
        listaTipos = new ArrayList<>();
        Cursor cursor = databaseHelper.obtenerTiposMantenimiento();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    listaTipos.add(
                            new TipoMantenimiento(
                                    cursor.getInt(0), // id_tipo
                                    cursor.getString(1) // nombre_tipo
                            )
                    );
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        ArrayList<String> nombresTipos = new ArrayList<>();
        for (TipoMantenimiento tipo : listaTipos) {
            nombresTipos.add(tipo.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombresTipos
        );

        spTipo.setAdapter(adapter);
    }

    private void guardarMantenimiento() {

        String fecha = txtFecha.getText().toString().trim();
        String kilometrajeStr = txtKilometraje.getText().toString().trim();
        String costoStr = txtCosto.getText().toString().trim();
        String descripcion = txtDescripcion.getText().toString().trim();
        String tipoSeleccionadoStr = spTipo.getText().toString().trim();

        if (tipoSeleccionadoStr.isEmpty()
                || fecha.isEmpty()
                || kilometrajeStr.isEmpty()
                || costoStr.isEmpty()) {

            Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        int idTipoSeleccionado = -1;

        for (TipoMantenimiento tipo : listaTipos) {

            if (tipo.getNombre().equals(tipoSeleccionadoStr)) {

                idTipoSeleccionado = tipo.getId();
                break;

            }

        }

        if (idTipoSeleccionado == -1) {

            Toast.makeText(
                    this,
                    "Seleccione un mantenimiento válido",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }

        int kilometraje = Integer.parseInt(kilometrajeStr);
        int ultimoKm =
                databaseHelper.obtenerUltimoKilometrajeVehiculo(idVehiculo);

        if (ultimoKm > 0 && kilometraje <= ultimoKm) {

            Toast.makeText(

                    this,

                    "El kilometraje debe ser mayor que el último registrado (" +
                            ultimoKm + " km)",

                    Toast.LENGTH_LONG

            ).show();

            return;

        }

        double costo = Double.parseDouble(costoStr);

        Mantenimiento mantenimiento = new Mantenimiento();

        mantenimiento.setIdVehiculo(idVehiculo);
        mantenimiento.setIdTipo(idTipoSeleccionado);
        mantenimiento.setFecha(fecha);
        mantenimiento.setKilometraje(kilometraje);
        mantenimiento.setDescripcion(descripcion);

        long idMantenimiento =
                databaseHelper.insertarMantenimiento(mantenimiento);

        if (idMantenimiento != -1) {

            Gasto gasto = new Gasto();

            gasto.setIdMantenimiento((int) idMantenimiento);
            gasto.setMonto(costo);
            gasto.setCategoria(tipoSeleccionadoStr);

            databaseHelper.insertarGasto(gasto);

            Toast.makeText(
                    this,
                    "Mantenimiento registrado",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Error al guardar",
                    Toast.LENGTH_SHORT
            ).show();

        }

    }
    private void mostrarCalendario() {
        Calendar calendario = Calendar.getInstance();

        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String fecha = String.format(
                            "%02d/%02d/%04d",
                            dayOfMonth,
                            month + 1,
                            year
                    );
                    txtFecha.setText(fecha);
                },
                anio,
                mes,
                dia
        );

        datePickerDialog.show();
    }
}


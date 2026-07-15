package com.autocare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.autocare.R;
import com.autocare.adapters.MantenimientoAdapter;
import com.autocare.database.DatabaseHelper;
import com.autocare.models.Mantenimiento;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MantenimientoActivity extends AppCompatActivity {

    private TextView lblVehicleName;
    private TextView txtTotalServicios;
    private TextView txtUltimoKilometraje;

    private RecyclerView recyclerMantenimientos;
    private MaterialButton btnAgregarMantenimiento;

    private DatabaseHelper databaseHelper;

    private int idVehiculo;
    private String nombreVehiculo;
    private String placa;
    private int anio;

    private boolean modoGeneral = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento);

        lblVehicleName = findViewById(R.id.lblVehicleName);
        txtTotalServicios = findViewById(R.id.txtTotalServicios);
        txtUltimoKilometraje = findViewById(R.id.txtUltimoKilometraje);

        recyclerMantenimientos = findViewById(R.id.recyclerMantenimientos);
        btnAgregarMantenimiento = findViewById(R.id.btnAgregarMantenimiento);

        databaseHelper = new DatabaseHelper(this);

        modoGeneral = getIntent().getBooleanExtra("modoGeneral", false);

        if (modoGeneral) {

            lblVehicleName.setText("TODOS LOS VEHÍCULOS");

            btnAgregarMantenimiento.setVisibility(View.GONE);

        } else {

            idVehiculo = getIntent().getIntExtra("idVehiculo", -1);
            nombreVehiculo = getIntent().getStringExtra("vehiculo");
            placa = getIntent().getStringExtra("placa");
            anio = getIntent().getIntExtra("anio", 0);

            lblVehicleName.setText(
                    nombreVehiculo +
                            " | " +
                            placa +
                            " | " +
                            anio
            );

            btnAgregarMantenimiento.setOnClickListener(v -> {

                Intent intent = new Intent(
                        MantenimientoActivity.this,
                        RegistrarMantenimientoActivity.class
                );

                intent.putExtra("idVehiculo", idVehiculo);
                intent.putExtra("vehiculo", nombreVehiculo);
                intent.putExtra("placa", placa);
                intent.putExtra("anio", anio);

                startActivity(intent);

            });

        }

        cargarHistorial();

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarHistorial();
    }

    private void cargarHistorial() {

        ArrayList<Mantenimiento> lista;

        if (modoGeneral) {

            lista = databaseHelper.obtenerTodosLosMantenimientos();

        } else {

            lista = databaseHelper.obtenerHistorialMantenimiento(idVehiculo);

        }

        MantenimientoAdapter adapter =
                new MantenimientoAdapter(lista);

        recyclerMantenimientos.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerMantenimientos.setAdapter(adapter);

        txtTotalServicios.setText(String.valueOf(lista.size()));

        if (lista.size() > 0) {

            txtUltimoKilometraje.setText(
                    lista.get(0).getKilometraje() + " km"
            );

        } else {

            txtUltimoKilometraje.setText("0 km");

        }

    }

}
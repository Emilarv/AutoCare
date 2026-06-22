package com.autocare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.R;
import com.autocare.database.DatabaseHelper;
import com.autocare.models.Vehiculo;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    Button btnVehiculos;

    TextView txtDashVehiculosRegistrados;
    TextView txtDashMantenimientosPendientes;
    TextView txtDashGastosMes;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnVehiculos = findViewById(R.id.btnVehiculos);
        txtDashVehiculosRegistrados = findViewById(R.id.txtDashVehiculosRegistrados);
        txtDashMantenimientosPendientes = findViewById(R.id.txtDashMantenimientosPendientes);
        txtDashGastosMes = findViewById(R.id.txtDashGastosMes);

        databaseHelper = new DatabaseHelper(this);

        btnVehiculos.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, VehiculoActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarResumenGeneral();
    }

    private void actualizarResumenGeneral() {
        ArrayList<Vehiculo> listaVehiculos = databaseHelper.obtenerVehiculos();
        int cantidadVehiculos = listaVehiculos.size();

        txtDashVehiculosRegistrados.setText("Vehículos registrados: " + cantidadVehiculos);

        txtDashMantenimientosPendientes.setText("Mantenimientos pendientes: 0");
        txtDashGastosMes.setText("Gastos este mes: RD$ 0");
    }
}
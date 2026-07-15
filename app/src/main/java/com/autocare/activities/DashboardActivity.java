package com.autocare.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.R;
import com.autocare.database.DatabaseHelper;
import com.autocare.models.Mantenimiento;
import com.autocare.models.Vehiculo;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    MaterialButton btnVehiculos;
    MaterialButton btnMantenimientos;
    MaterialButton btnGastos;

    TextView txtDashVehiculosRegistrados;
    TextView txtDashMantenimientosPendientes;
    TextView txtDashGastosMes;

    LinearLayout layoutRecordatorios;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnVehiculos = findViewById(R.id.btnVehiculos);
        btnMantenimientos = findViewById(R.id.btnMantenimientos);
        btnGastos = findViewById(R.id.btnGastos);

        txtDashVehiculosRegistrados = findViewById(R.id.txtDashVehiculosRegistrados);
        txtDashMantenimientosPendientes = findViewById(R.id.txtDashMantenimientosPendientes);
        txtDashGastosMes = findViewById(R.id.txtDashGastosMes);

        layoutRecordatorios = findViewById(R.id.layoutRecordatorios);

        databaseHelper = new DatabaseHelper(this);

        btnVehiculos.setOnClickListener(v -> {

            Intent intent = new Intent(
                    DashboardActivity.this,
                    VehiculoActivity.class
            );

            startActivity(intent);

        });

        btnMantenimientos.setOnClickListener(v -> {

            Intent intent = new Intent(
                    DashboardActivity.this,
                    MantenimientoActivity.class
            );

            intent.putExtra("modoGeneral", true);

            startActivity(intent);

        });

        btnGastos.setOnClickListener(v -> {

            Intent intent = new Intent(
                    DashboardActivity.this,
                    GastosActivity.class
            );

            intent.putExtra("historialGeneral", true);

            startActivity(intent);

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        actualizarResumenGeneral();
        cargarRecordatorios();

    }

    private void actualizarResumenGeneral() {

        int cantidadVehiculos =
                databaseHelper.obtenerVehiculos().size();

        int cantidadMantenimientos =
                databaseHelper.obtenerCantidadMantenimientos();

        double totalGastado =
                databaseHelper.obtenerTotalGastado();

        txtDashVehiculosRegistrados.setText(
                "Vehículos registrados: " + cantidadVehiculos
        );

        txtDashMantenimientosPendientes.setText(
                "Mantenimientos realizados: " + cantidadMantenimientos
        );

        txtDashGastosMes.setText(
                "Total invertido: RD$ " +
                        String.format("%.2f", totalGastado)
        );

    }

    private void cargarRecordatorios() {

        layoutRecordatorios.removeAllViews();

        ArrayList<Vehiculo> vehiculos =
                databaseHelper.obtenerVehiculos();

        if (vehiculos.isEmpty()) {

            TextView txt = new TextView(this);

            txt.setText("No hay vehículos registrados.");
            txt.setTextColor(Color.parseColor("#D32F2F"));
            txt.setTextSize(14);

            layoutRecordatorios.addView(txt);

            return;
        }

        for (Vehiculo vehiculo : vehiculos) {

            ArrayList<Mantenimiento> historial =
                    databaseHelper.obtenerHistorialMantenimiento(
                            vehiculo.getId()
                    );

            TextView txt = new TextView(this);

            txt.setTextColor(Color.parseColor("#D32F2F"));
            txt.setTextSize(14);

            if (historial.isEmpty()) {

                txt.setText(
                        "• " +
                                vehiculo.getMarca() +
                                " " +
                                vehiculo.getModelo() +
                                "\nSin mantenimientos registrados."
                );

            } else {

                Mantenimiento ultimo = historial.get(0);

                txt.setText(
                        "• " +
                                vehiculo.getMarca() +
                                " " +
                                vehiculo.getModelo() +
                                "\nÚltimo servicio: " +
                                ultimo.getNombreTipo() +
                                "\n" +
                                ultimo.getKilometraje() +
                                " km"
                );

            }

            txt.setPadding(0, 0, 0, 20);

            layoutRecordatorios.addView(txt);

        }

    }

}
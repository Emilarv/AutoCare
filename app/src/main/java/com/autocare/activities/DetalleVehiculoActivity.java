package com.autocare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.R;
import com.autocare.database.DatabaseHelper;
import com.autocare.models.Mantenimiento;

import java.util.ArrayList;

public class DetalleVehiculoActivity extends AppCompatActivity {

    private TextView txtVehiculo;
    private TextView txtPlaca;
    private TextView txtKilometraje;
    private TextView txtEstado;

    private Button btnMantenimiento;
    private Button btnAgregarMantenimiento;

    private LinearLayout layoutHistorial;

    private int idVehiculo;

    private String nombreVehiculo;
    private String placa;
    private int anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_vehiculo);

        txtVehiculo = findViewById(R.id.txtVehiculo);

        txtPlaca = findViewById(R.id.txtPlaca);
        txtKilometraje = findViewById(R.id.txtKilometraje);
        txtEstado = findViewById(R.id.txtEstado);

        btnMantenimiento = findViewById(R.id.btnMantenimiento);
        btnAgregarMantenimiento = findViewById(R.id.btnAgregarMantenimiento);

        layoutHistorial = findViewById(R.id.layoutHistorial);

        // Datos recibidos
        idVehiculo = getIntent().getIntExtra("idVehiculo", -1);

        nombreVehiculo = getIntent().getStringExtra("vehiculo");
        placa = getIntent().getStringExtra("placa");
        anio = getIntent().getIntExtra("anio", 0);

        if (nombreVehiculo != null) {

            txtVehiculo.setText(
                    nombreVehiculo +
                            "\nPlaca: " + placa +
                            "   |   Año: " + anio
            );

        }

        cargarInformacionGeneral();
        cargarHistorialReciente();

        btnMantenimiento.setOnClickListener(v -> {

            Intent intent = new Intent(
                    DetalleVehiculoActivity.this,
                    MantenimientoActivity.class
            );

            intent.putExtra("idVehiculo", idVehiculo);
            intent.putExtra("vehiculo", nombreVehiculo);
            intent.putExtra("placa", placa);
            intent.putExtra("anio", anio);

            startActivity(intent);

        });

        btnAgregarMantenimiento.setOnClickListener(v -> {

            Intent intent = new Intent(
                    DetalleVehiculoActivity.this,
                    RegistrarMantenimientoActivity.class
            );

            intent.putExtra("idVehiculo", idVehiculo);
            intent.putExtra("vehiculo", nombreVehiculo);
            intent.putExtra("placa", placa);
            intent.putExtra("anio", anio);

            startActivity(intent);

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        cargarInformacionGeneral();
        cargarHistorialReciente();
    }

    private void cargarInformacionGeneral() {

        DatabaseHelper db = new DatabaseHelper(this);

        txtPlaca.setText(placa);

        int ultimoKm = db.obtenerUltimoKilometrajeVehiculo(idVehiculo);

        txtKilometraje.setText(ultimoKm + " km");

        double total = db.obtenerTotalGastadoVehiculo(idVehiculo);

        txtEstado.setText("$ " + String.format("%.2f", total));

    }

    private void cargarHistorialReciente() {

        layoutHistorial.removeAllViews();

        DatabaseHelper db = new DatabaseHelper(this);

        ArrayList<Mantenimiento> lista =
                db.obtenerHistorialMantenimiento(idVehiculo);

        if (lista.isEmpty()) {

            TextView txt = new TextView(this);

            txt.setText("No se encontraron mantenimientos registrados para este vehículo.");
            txt.setTextSize(14);
            txt.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt.setPadding(0, 20, 0, 20);

            layoutHistorial.addView(txt);

            return;
        }

        int limite = Math.min(3, lista.size());

        for (int i = 0; i < limite; i++) {

            Mantenimiento m = lista.get(i);

            TextView item = new TextView(this);

            item.setText(
                    "• " +
                            m.getNombreTipo() +
                            "\n" +
                            m.getFecha() +
                            "   |   " +
                            m.getKilometraje() +
                            " km"
            );

            item.setTextSize(15);
            item.setPadding(0, 12, 0, 12);

            layoutHistorial.addView(item);
        }

    }

}
package com.autocare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.R;

public class DetalleVehiculoActivity extends AppCompatActivity {

    TextView txtVehiculo;

    Button btnMantenimiento;
    Button btnAgregarMantenimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_vehiculo);

        txtVehiculo = findViewById(R.id.txtVehiculo);

        btnMantenimiento = findViewById(R.id.btnMantenimiento);
        btnAgregarMantenimiento = findViewById(R.id.btnAgregarMantenimiento);

        String vehiculo = getIntent().getStringExtra("vehiculo");

        if (vehiculo != null) {
            txtVehiculo.setText(vehiculo);
        }

        btnMantenimiento.setOnClickListener(v -> {

            Intent intent = new Intent(
                    DetalleVehiculoActivity.this,
                    MantenimientoActivity.class
            );

            startActivity(intent);

        });

        btnAgregarMantenimiento.setOnClickListener(v -> {

            Intent intent = new Intent(
                    DetalleVehiculoActivity.this,
                    MantenimientoActivity.class
            );

            startActivity(intent);

        });
    }
}
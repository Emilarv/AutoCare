package com.autocare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.R;

import com.google.android.material.card.MaterialCardView;

public class VehiculoActivity extends AppCompatActivity {

    Button btnAgregarVehiculo;

    MaterialCardView cardToyota;
    MaterialCardView cardHonda;
    MaterialCardView cardHyundai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        btnAgregarVehiculo = findViewById(R.id.btnAgregarVehiculo);

        cardToyota = findViewById(R.id.cardToyota);
        cardHonda = findViewById(R.id.cardHonda);
        cardHyundai = findViewById(R.id.cardHyundai);

        btnAgregarVehiculo.setOnClickListener(v -> {
            Intent intent = new Intent(
                    VehiculoActivity.this,
                    RegisterActivity.class
            );
            startActivity(intent);
        });

        cardToyota.setOnClickListener(v -> abrirDetalle("Toyota Corolla 2020"));
        cardHonda.setOnClickListener(v -> abrirDetalle("Honda Civic 2021"));
        cardHyundai.setOnClickListener(v -> abrirDetalle("Hyundai Elantra 2022"));
    }

    private void abrirDetalle(String vehiculo) {
        Intent intent = new Intent(
                VehiculoActivity.this,
                DetalleVehiculoActivity.class
        );
        intent.putExtra("vehiculo", vehiculo);
        startActivity(intent);
    }
}
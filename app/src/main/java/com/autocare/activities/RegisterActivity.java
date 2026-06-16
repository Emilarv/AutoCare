package com.autocare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.autocare.R;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText txtMarca;
    TextInputEditText txtModelo;
    TextInputEditText txtAnio;
    TextInputEditText txtPlaca;

    Button btnGuardarVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtMarca = findViewById(R.id.txtMarca);
        txtModelo = findViewById(R.id.txtModelo);
        txtAnio = findViewById(R.id.txtAnio);
        txtPlaca = findViewById(R.id.txtPlaca);

        btnGuardarVehiculo = findViewById(R.id.btnGuardarVehiculo);

        btnGuardarVehiculo.setOnClickListener(v -> {

            String marca = txtMarca.getText().toString();
            String modelo = txtModelo.getText().toString();
            String anio = txtAnio.getText().toString();
            String placa = txtPlaca.getText().toString();

            Toast.makeText(
                    RegisterActivity.this,
                    "Vehículo agregado correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(
                    RegisterActivity.this,
                    DashboardActivity.class
            );

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(intent);

            finish();
        });
    }
}
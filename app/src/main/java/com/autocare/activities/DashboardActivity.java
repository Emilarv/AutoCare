package com.autocare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.R;

public class DashboardActivity extends AppCompatActivity {

    Button btnVehiculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnVehiculos = findViewById(R.id.btnVehiculos);

        btnVehiculos.setOnClickListener(v -> {

            Intent intent = new Intent(
                    DashboardActivity.this,
                    VehiculoActivity.class
            );

            startActivity(intent);

        });
    }
}
package com.autocare.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.R;
import com.autocare.database.DatabaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditarVehiculoActivity extends AppCompatActivity {

    TextInputEditText txtMarca;
    TextInputEditText txtModelo;
    TextInputEditText txtAnio;
    TextInputEditText txtPlaca;

    MaterialButton btnActualizar;

    DatabaseHelper databaseHelper;

    int idVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vehiculo);

        txtMarca = findViewById(R.id.txtMarca);
        txtModelo = findViewById(R.id.txtModelo);
        txtAnio = findViewById(R.id.txtAnio);
        txtPlaca = findViewById(R.id.txtPlaca);

        btnActualizar = findViewById(R.id.btnActualizar);

        databaseHelper = new DatabaseHelper(this);

        // Datos recibidos desde VehiculoActivity
        idVehiculo = getIntent().getIntExtra("id", 0);

        txtMarca.setText(
                getIntent().getStringExtra("marca")
        );

        txtModelo.setText(
                getIntent().getStringExtra("modelo")
        );

        txtAnio.setText(
                String.valueOf(
                        getIntent().getIntExtra("anio", 0)
                )
        );

        txtPlaca.setText(
                getIntent().getStringExtra("placa")
        );

        btnActualizar.setOnClickListener(v -> {

            String marca = txtMarca.getText().toString().trim();
            String modelo = txtModelo.getText().toString().trim();
            String anioTexto = txtAnio.getText().toString().trim();
            String placa = txtPlaca.getText().toString().trim();

            if (marca.isEmpty()
                    || modelo.isEmpty()
                    || anioTexto.isEmpty()
                    || placa.isEmpty()) {

                Toast.makeText(
                        this,
                        "Complete todos los campos",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            int anio = Integer.parseInt(anioTexto);

            boolean actualizado =
                    databaseHelper.actualizarVehiculo(
                            idVehiculo,
                            marca,
                            modelo,
                            anio,
                            placa
                    );

            if (actualizado) {

                Toast.makeText(
                        this,
                        "Vehículo actualizado correctamente",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "No se pudo actualizar el vehículo",
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

    }
}
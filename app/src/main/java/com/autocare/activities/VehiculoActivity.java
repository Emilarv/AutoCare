package com.autocare.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.autocare.R;
import com.autocare.adapters.VehiculoAdapter;
import com.autocare.database.DatabaseHelper;
import com.autocare.models.Vehiculo;

import java.util.ArrayList;

public class VehiculoActivity extends AppCompatActivity {

    Button btnAgregarVehiculo;

    TextView txtCantidadVehiculos;
    TextView txtUltimoMantenimiento;

    RecyclerView recyclerVehiculos;

    DatabaseHelper databaseHelper;

    ArrayList<Vehiculo> listaVehiculos;

    VehiculoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        btnAgregarVehiculo = findViewById(R.id.btnAgregarVehiculo);

        txtCantidadVehiculos = findViewById(R.id.txtCantidadVehiculos);
        txtUltimoMantenimiento = findViewById(R.id.txtUltimoMantenimiento);

        recyclerVehiculos = findViewById(R.id.recyclerVehiculos);

        recyclerVehiculos.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);

        cargarVehiculos();

        btnAgregarVehiculo.setOnClickListener(v -> {

            Intent intent = new Intent(
                    VehiculoActivity.this,
                    RegisterActivity.class
            );

            startActivity(intent);

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarVehiculos();
    }

    private void cargarVehiculos() {

        listaVehiculos = databaseHelper.obtenerVehiculos();

        txtCantidadVehiculos.setText(
                "Vehículos registrados: " + listaVehiculos.size()
        );

        String ultimaFecha = databaseHelper.obtenerUltimaFechaMantenimiento();

        txtUltimoMantenimiento.setText(
                "Último mantenimiento: " + ultimaFecha
        );

        adapter = new VehiculoAdapter(
                listaVehiculos,
                new VehiculoAdapter.OnVehiculoClickListener() {

                    @Override
                    public void onVehiculoClick(Vehiculo vehiculo) {

                        Intent intent = new Intent(
                                VehiculoActivity.this,
                                DetalleVehiculoActivity.class
                        );

                        intent.putExtra(
                                "idVehiculo",
                                vehiculo.getId()
                        );

                        intent.putExtra(
                                "vehiculo",
                                vehiculo.getMarca() + " " + vehiculo.getModelo()
                        );

                        intent.putExtra(
                                "placa",
                                vehiculo.getPlaca()
                        );

                        intent.putExtra(
                                "anio",
                                vehiculo.getAnio()
                        );

                        startActivity(intent);

                    }

                    @Override
                    public void onEditarClick(Vehiculo vehiculo) {

                        Intent intent = new Intent(
                                VehiculoActivity.this,
                                EditarVehiculoActivity.class
                        );

                        intent.putExtra("id", vehiculo.getId());
                        intent.putExtra("marca", vehiculo.getMarca());
                        intent.putExtra("modelo", vehiculo.getModelo());
                        intent.putExtra("anio", vehiculo.getAnio());
                        intent.putExtra("placa", vehiculo.getPlaca());

                        startActivity(intent);

                    }

                    @Override
                    public void onEliminarClick(Vehiculo vehiculo) {

                        new AlertDialog.Builder(VehiculoActivity.this)
                                .setTitle("Eliminar vehículo")
                                .setMessage(
                                        "¿Deseas eliminar "
                                                + vehiculo.getMarca()
                                                + " "
                                                + vehiculo.getModelo()
                                                + "?"
                                )
                                .setPositiveButton("Sí", (dialog, which) -> {

                                    boolean eliminado =
                                            databaseHelper.eliminarVehiculo(
                                                    vehiculo.getId()
                                            );

                                    if (eliminado) {

                                        Toast.makeText(
                                                VehiculoActivity.this,
                                                "Vehículo eliminado",
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        cargarVehiculos();

                                    }

                                })
                                .setNegativeButton("Cancelar", null)
                                .show();

                    }

                });

        recyclerVehiculos.setAdapter(adapter);

    }

}

package com.autocare.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.autocare.R;
import com.autocare.adapters.VehiculoAdapter;
import com.autocare.database.DatabaseHelper;
import com.autocare.models.Vehiculo;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class VehiculoActivity extends AppCompatActivity {

    private Button btnAgregarVehiculo;
    private ImageButton btnBack;

    private TextView txtCantidadVehiculos;
    private TextView txtUltimoMantenimiento;

    private RecyclerView recyclerVehiculos;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private DatabaseHelper databaseHelper;

    private ArrayList<Vehiculo> listaVehiculos;
    private VehiculoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        btnBack = findViewById(R.id.btnBack);

        ImageButton btnMenu = findViewById(R.id.btnMenu);

        btnMenu.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));

        btnAgregarVehiculo = findViewById(R.id.btnAgregarVehiculo);

        txtCantidadVehiculos = findViewById(R.id.txtCantidadVehiculos);
        txtUltimoMantenimiento = findViewById(R.id.txtUltimoMantenimiento);

        recyclerVehiculos = findViewById(R.id.recyclerVehiculos);
        recyclerVehiculos.setLayoutManager(new LinearLayoutManager(this));

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        databaseHelper = new DatabaseHelper(this);

        configurarMenuLateral();
        configurarBotonAtras();

        btnBack.setOnClickListener(v -> finish());

        btnAgregarVehiculo.setOnClickListener(v -> {

            Intent intent = new Intent(
                    VehiculoActivity.this,
                    RegisterActivity.class
            );

            startActivity(intent);

        });

        cargarVehiculos();
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

        String ultimaFecha =
                databaseHelper.obtenerUltimaFechaMantenimiento();

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

                        intent.putExtra("idVehiculo", vehiculo.getId());

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

    private void configurarMenuLateral() {

        navigationView.setCheckedItem(R.id.nav_vehiculos);

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {

                startActivity(new Intent(this, DashboardActivity.class));

            } else if (id == R.id.nav_vehiculos) {

                drawerLayout.closeDrawer(GravityCompat.START);

            } else if (id == R.id.nav_mantenimientos) {

                Intent intent = new Intent(this, MantenimientoActivity.class);
                intent.putExtra("modoGeneral", true);
                startActivity(intent);

            } else if (id == R.id.nav_gastos) {

                Intent intent = new Intent(this, GastosActivity.class);
                intent.putExtra("historialGeneral", true);
                startActivity(intent);

            } else if (id == R.id.nav_perfil) {

                new AlertDialog.Builder(this)
                        .setTitle("Mi Perfil")
                        .setMessage("Próximamente.")
                        .setPositiveButton("Aceptar", null)
                        .show();

            } else if (id == R.id.nav_acerca) {

                new AlertDialog.Builder(this)
                        .setTitle("AutoCare")
                        .setMessage(
                                "Versión 1.0\n\nAplicación para la gestión de mantenimiento vehicular."
                        )
                        .setPositiveButton("Aceptar", null)
                        .show();

            } else if (id == R.id.nav_logout) {

                SharedPreferences preferencias =
                        getSharedPreferences(
                                "AutoCarePrefs",
                                MODE_PRIVATE
                        );

                preferencias.edit().clear().apply();

                Intent intent = new Intent(
                        this,
                        LoginActivity.class
                );

                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                );

                startActivity(intent);
                finish();

            }

            drawerLayout.closeDrawer(GravityCompat.START);

            return true;

        });

    }

    private void configurarBotonAtras() {

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

                            drawerLayout.closeDrawer(GravityCompat.START);

                        } else {

                            finish();

                        }

                    }

                });

    }

}
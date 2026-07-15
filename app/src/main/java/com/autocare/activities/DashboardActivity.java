package com.autocare.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.autocare.R;
import com.autocare.database.DatabaseHelper;
import com.autocare.models.Mantenimiento;
import com.autocare.models.Vehiculo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    private MaterialButton btnMenu;
    private MaterialButton btnVehiculos;
    private MaterialButton btnMantenimientos;
    private MaterialButton btnGastos;

    private TextView txtBienvenida;
    private TextView txtDashVehiculosRegistrados;
    private TextView txtDashMantenimientosPendientes;
    private TextView txtDashGastosMes;

    private TextView txtNombreUsuario;
    private TextView txtCorreoUsuario;

    private LinearLayout layoutRecordatorios;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private DatabaseHelper databaseHelper;
    private SharedPreferences preferencias;

    private int idUsuario;
    private String nombreUsuario;
    private String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        vincularVistas();

        databaseHelper = new DatabaseHelper(this);

        preferencias = getSharedPreferences(
                "AutoCarePrefs",
                MODE_PRIVATE
        );

        cargarDatosSesion();
        configurarEncabezadoMenu();
        configurarBotonesDashboard();
        configurarMenuLateral();
        configurarBotonAtras();
    }

    private void vincularVistas() {

        btnMenu = findViewById(R.id.btnMenu);
        btnVehiculos = findViewById(R.id.btnVehiculos);
        btnMantenimientos = findViewById(R.id.btnMantenimientos);
        btnGastos = findViewById(R.id.btnGastos);

        txtBienvenida = findViewById(R.id.txtBienvenida);

        txtDashVehiculosRegistrados =
                findViewById(R.id.txtDashVehiculosRegistrados);

        txtDashMantenimientosPendientes =
                findViewById(R.id.txtDashMantenimientosPendientes);

        txtDashGastosMes =
                findViewById(R.id.txtDashGastosMes);

        layoutRecordatorios =
                findViewById(R.id.layoutRecordatorios);

        drawerLayout =
                findViewById(R.id.drawerLayout);

        navigationView =
                findViewById(R.id.navigationView);
    }

    private void cargarDatosSesion() {

        idUsuario = preferencias.getInt(
                "idUsuario",
                -1
        );

        nombreUsuario = preferencias.getString(
                "nombreUsuario",
                "Usuario"
        );

        correoUsuario = preferencias.getString(
                "correoUsuario",
                ""
        );

        txtBienvenida.setText(
                "Bienvenido, " + nombreUsuario
        );
    }

    private void configurarEncabezadoMenu() {

        View headerView = navigationView.getHeaderView(0);

        txtNombreUsuario =
                headerView.findViewById(R.id.txtNombreUsuario);

        txtCorreoUsuario =
                headerView.findViewById(R.id.txtCorreoUsuario);

        txtNombreUsuario.setText(nombreUsuario);

        if (correoUsuario == null || correoUsuario.trim().isEmpty()) {
            txtCorreoUsuario.setText("Sin correo disponible");
        } else {
            txtCorreoUsuario.setText(correoUsuario);
        }
    }

    private void configurarBotonesDashboard() {

        btnMenu.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START)
        );

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

    private void configurarMenuLateral() {

        navigationView.setCheckedItem(R.id.nav_dashboard);

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {

                drawerLayout.closeDrawer(GravityCompat.START);

            } else if (id == R.id.nav_vehiculos) {

                Intent intent = new Intent(
                        DashboardActivity.this,
                        VehiculoActivity.class
                );

                startActivity(intent);

            } else if (id == R.id.nav_mantenimientos) {

                Intent intent = new Intent(
                        DashboardActivity.this,
                        MantenimientoActivity.class
                );

                intent.putExtra("modoGeneral", true);

                startActivity(intent);

            } else if (id == R.id.nav_gastos) {

                Intent intent = new Intent(
                        DashboardActivity.this,
                        GastosActivity.class
                );

                intent.putExtra("historialGeneral", true);

                startActivity(intent);

            } else if (id == R.id.nav_perfil) {

                mostrarPerfil();

            } else if (id == R.id.nav_acerca) {

                mostrarAcercaDe();

            } else if (id == R.id.nav_logout) {

                mostrarDialogoCerrarSesion();
            }

            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        });
    }

    private void mostrarPerfil() {

        String correoMostrar =
                correoUsuario == null || correoUsuario.trim().isEmpty()
                        ? "No disponible"
                        : correoUsuario;

        new AlertDialog.Builder(this)
                .setTitle("Mi Perfil")
                .setMessage(
                        "Nombre: " + nombreUsuario +
                                "\n\nCorreo: " + correoMostrar +
                                "\n\nID de usuario: " + idUsuario +
                                "\n\nEstado: Activo"
                )
                .setPositiveButton("Aceptar", null)
                .show();
    }

    private void mostrarAcercaDe() {

        new AlertDialog.Builder(this)
                .setTitle("Acerca de AutoCare")
                .setMessage(
                        "AutoCare\n\n" +
                                "Versión 1.0\n\n" +
                                "Aplicación móvil para gestionar vehículos, " +
                                "mantenimientos y gastos relacionados con el " +
                                "cuidado vehicular.\n\n" +
                                "Desarrollador: Emil Rodriguez\n" +
                                "Asignatura: Seminario de Proyecto II (ISW-411)"
                )
                .setPositiveButton("Aceptar", null)
                .show();
    }

    private void mostrarDialogoCerrarSesion() {

        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Desea cerrar la sesión actual?")
                .setPositiveButton("Sí", (dialog, which) -> cerrarSesion())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void cerrarSesion() {

        preferencias.edit()
                .clear()
                .apply();

        Intent intent = new Intent(
                DashboardActivity.this,
                LoginActivity.class
        );

        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);
        finish();
    }

    private void configurarBotonAtras() {

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        if (drawerLayout.isDrawerOpen(
                                GravityCompat.START
                        )) {

                            drawerLayout.closeDrawer(
                                    GravityCompat.START
                            );

                        } else {

                            setEnabled(false);

                            getOnBackPressedDispatcher()
                                    .onBackPressed();
                        }
                    }
                }
        );
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
                "Mantenimientos realizados: "
                        + cantidadMantenimientos
        );

        txtDashGastosMes.setText(
                "Total invertido: RD$ "
                        + String.format(
                        Locale.getDefault(),
                        "%.2f",
                        totalGastado
                )
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
                        "• "
                                + vehiculo.getMarca()
                                + " "
                                + vehiculo.getModelo()
                                + "\nSin mantenimientos registrados."
                );

            } else {

                Mantenimiento ultimo = historial.get(0);

                txt.setText(
                        "• "
                                + vehiculo.getMarca()
                                + " "
                                + vehiculo.getModelo()
                                + "\nÚltimo servicio: "
                                + ultimo.getNombreTipo()
                                + "\n"
                                + ultimo.getKilometraje()
                                + " km"
                );
            }

            txt.setPadding(0, 0, 0, 20);

            layoutRecordatorios.addView(txt);
        }
    }
}
package com.autocare.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.autocare.R;
import com.autocare.database.DatabaseHelper;
import com.autocare.models.Mantenimiento;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Locale;

public class DetalleVehiculoActivity extends AppCompatActivity {

    private ImageButton btnMenu;

    private TextView txtVehiculo;
    private TextView txtPlaca;
    private TextView txtKilometraje;
    private TextView txtEstado;

    private Button btnMantenimiento;
    private Button btnAgregarMantenimiento;

    private LinearLayout layoutHistorial;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private DatabaseHelper databaseHelper;
    private SharedPreferences preferencias;

    private int idVehiculo;
    private String nombreVehiculo;
    private String placa;
    private int anio;

    private int idUsuario;
    private String nombreUsuario;
    private String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_vehiculo);

        vincularVistas();

        databaseHelper = new DatabaseHelper(this);

        preferencias = getSharedPreferences(
                "AutoCarePrefs",
                MODE_PRIVATE
        );

        cargarDatosSesion();
        configurarEncabezadoMenu();
        recuperarDatosVehiculo();
        configurarBotones();
        configurarMenuLateral();
        configurarBotonAtras();

        cargarInformacionGeneral();
        cargarHistorialReciente();
    }

    private void vincularVistas() {

        btnMenu = findViewById(R.id.btnMenu);

        txtVehiculo = findViewById(R.id.txtVehiculo);
        txtPlaca = findViewById(R.id.txtPlaca);
        txtKilometraje = findViewById(R.id.txtKilometraje);
        txtEstado = findViewById(R.id.txtEstado);

        btnMantenimiento = findViewById(R.id.btnMantenimiento);
        btnAgregarMantenimiento = findViewById(R.id.btnAgregarMantenimiento);

        layoutHistorial = findViewById(R.id.layoutHistorial);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
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
    }

    private void configurarEncabezadoMenu() {

        View headerView = navigationView.getHeaderView(0);

        TextView txtNombreUsuario =
                headerView.findViewById(R.id.txtNombreUsuario);

        TextView txtCorreoUsuario =
                headerView.findViewById(R.id.txtCorreoUsuario);

        txtNombreUsuario.setText(nombreUsuario);

        if (correoUsuario == null || correoUsuario.trim().isEmpty()) {

            txtCorreoUsuario.setText("Sin correo disponible");

        } else {

            txtCorreoUsuario.setText(correoUsuario);
        }
    }

    private void recuperarDatosVehiculo() {

        idVehiculo = getIntent().getIntExtra(
                "idVehiculo",
                -1
        );

        nombreVehiculo = getIntent().getStringExtra(
                "vehiculo"
        );

        placa = getIntent().getStringExtra(
                "placa"
        );

        anio = getIntent().getIntExtra(
                "anio",
                0
        );

        if (nombreVehiculo == null || nombreVehiculo.trim().isEmpty()) {
            nombreVehiculo = "Vehículo";
        }

        if (placa == null || placa.trim().isEmpty()) {
            placa = "Sin placa";
        }

        txtVehiculo.setText(
                nombreVehiculo
                        + "\nPlaca: "
                        + placa
                        + "   |   Año: "
                        + anio
        );
    }

    private void configurarBotones() {

        btnMenu.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START)
        );

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

    private void configurarMenuLateral() {

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {

                abrirDashboard();

            } else if (id == R.id.nav_vehiculos) {

                abrirVehiculos();

            } else if (id == R.id.nav_mantenimientos) {

                abrirHistorialGeneralMantenimientos();

            } else if (id == R.id.nav_gastos) {

                abrirHistorialGeneralGastos();

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

    private void abrirDashboard() {

        Intent intent = new Intent(
                DetalleVehiculoActivity.this,
                DashboardActivity.class
        );

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();
    }

    private void abrirVehiculos() {

        Intent intent = new Intent(
                DetalleVehiculoActivity.this,
                VehiculoActivity.class
        );

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();
    }

    private void abrirHistorialGeneralMantenimientos() {

        Intent intent = new Intent(
                DetalleVehiculoActivity.this,
                MantenimientoActivity.class
        );

        intent.putExtra("modoGeneral", true);

        startActivity(intent);
    }

    private void abrirHistorialGeneralGastos() {

        Intent intent = new Intent(
                DetalleVehiculoActivity.this,
                GastosActivity.class
        );

        intent.putExtra("historialGeneral", true);

        startActivity(intent);
    }

    private void mostrarPerfil() {

        String correoMostrar;

        if (correoUsuario == null || correoUsuario.trim().isEmpty()) {
            correoMostrar = "No disponible";
        } else {
            correoMostrar = correoUsuario;
        }

        new AlertDialog.Builder(this)
                .setTitle("Mi Perfil")
                .setMessage(
                        "Nombre: " + nombreUsuario
                                + "\n\nCorreo: " + correoMostrar
                                + "\n\nID de usuario: " + idUsuario
                                + "\n\nEstado: Activo"
                )
                .setPositiveButton("Aceptar", null)
                .show();
    }

    private void mostrarAcercaDe() {

        new AlertDialog.Builder(this)
                .setTitle("Acerca de AutoCare")
                .setMessage(
                        "AutoCare\n\n"
                                + "Versión 1.0\n\n"
                                + "Aplicación móvil para gestionar vehículos, "
                                + "mantenimientos y gastos relacionados con el "
                                + "cuidado vehicular.\n\n"
                                + "Desarrollador: Emil Rodriguez\n"
                                + "Asignatura: Seminario de Proyecto II (ISW-411)"
                )
                .setPositiveButton("Aceptar", null)
                .show();
    }

    private void mostrarDialogoCerrarSesion() {

        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Desea cerrar la sesión actual?")
                .setPositiveButton(
                        "Sí",
                        (dialog, which) -> cerrarSesion()
                )
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void cerrarSesion() {

        preferencias.edit()
                .clear()
                .apply();

        Intent intent = new Intent(
                DetalleVehiculoActivity.this,
                LoginActivity.class
        );

        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
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

                            finish();
                        }
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        cargarInformacionGeneral();
        cargarHistorialReciente();
    }

    private void cargarInformacionGeneral() {

        txtPlaca.setText(placa);

        int ultimoKm =
                databaseHelper.obtenerUltimoKilometrajeVehiculo(
                        idVehiculo
                );

        txtKilometraje.setText(
                ultimoKm + " km"
        );

        double total =
                databaseHelper.obtenerTotalGastadoVehiculo(
                        idVehiculo
                );

        txtEstado.setText(
                "RD$ "
                        + String.format(
                        Locale.getDefault(),
                        "%.2f",
                        total
                )
        );
    }

    private void cargarHistorialReciente() {

        layoutHistorial.removeAllViews();

        ArrayList<Mantenimiento> lista =
                databaseHelper.obtenerHistorialMantenimiento(
                        idVehiculo
                );

        if (lista.isEmpty()) {

            TextView txt = new TextView(this);

            txt.setText(
                    "No se encontraron mantenimientos registrados "
                            + "para este vehículo."
            );

            txt.setTextSize(14);
            txt.setTextColor(Color.parseColor("#757575"));
            txt.setPadding(0, 20, 0, 20);

            layoutHistorial.addView(txt);

            return;
        }

        int limite = Math.min(
                3,
                lista.size()
        );

        for (int i = 0; i < limite; i++) {

            Mantenimiento mantenimiento = lista.get(i);

            TextView item = new TextView(this);

            item.setText(
                    "• "
                            + mantenimiento.getNombreTipo()
                            + "\n"
                            + mantenimiento.getFecha()
                            + "   |   "
                            + mantenimiento.getKilometraje()
                            + " km"
            );

            item.setTextSize(15);
            item.setTextColor(Color.parseColor("#424242"));
            item.setPadding(0, 12, 0, 12);

            layoutHistorial.addView(item);
        }
    }
}
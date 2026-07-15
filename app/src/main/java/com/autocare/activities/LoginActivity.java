package com.autocare.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.R;
import com.autocare.database.DatabaseHelper;
import com.autocare.models.Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText txtCorreo;
    private TextInputEditText txtPassword;

    private MaterialButton btnLogin;
    private Button btnRegister;

    private DatabaseHelper databaseHelper;
    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        databaseHelper = new DatabaseHelper(this);

        preferencias = getSharedPreferences(
                "AutoCarePrefs",
                MODE_PRIVATE
        );

        btnRegister.setOnClickListener(v -> {

            Intent intent = new Intent(
                    LoginActivity.this,
                    RegistroUsuarioActivity.class
            );

            startActivity(intent);

        });

        btnLogin.setOnClickListener(v -> iniciarSesion());
    }

    private void iniciarSesion() {

        String correo = txtCorreo.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (correo.isEmpty() || password.isEmpty()) {

            Toast.makeText(
                    this,
                    "Complete todos los campos.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Usuario usuario = databaseHelper.iniciarSesion(
                correo,
                password
        );

        if (usuario != null) {

            guardarSesion(usuario);

            Toast.makeText(
                    this,
                    "Bienvenido " + usuario.getNombre(),
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(
                    LoginActivity.this,
                    DashboardActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
            finish();

        } else {

            Toast.makeText(
                    this,
                    "Correo o contraseña incorrectos.",
                    Toast.LENGTH_SHORT
            ).show();

        }

    }

    private void guardarSesion(Usuario usuario) {

        SharedPreferences.Editor editor = preferencias.edit();

        editor.putBoolean("sesionIniciada", true);
        editor.putInt("idUsuario", usuario.getIdUsuario());
        editor.putString("nombreUsuario", usuario.getNombre());
        editor.putString("correoUsuario", usuario.getCorreo());

        editor.apply();
    }
}
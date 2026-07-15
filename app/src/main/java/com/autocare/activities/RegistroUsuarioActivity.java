package com.autocare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.R;
import com.autocare.database.DatabaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegistroUsuarioActivity extends AppCompatActivity {

    private TextInputEditText txtNombre;
    private TextInputEditText txtCorreo;
    private TextInputEditText txtPassword;
    private TextInputEditText txtConfirmarPassword;

    private MaterialButton btnCrearCuenta;
    private Button btnVolverLogin;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        txtNombre = findViewById(R.id.txtNombre);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmarPassword = findViewById(R.id.txtConfirmarPassword);

        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        btnVolverLogin = findViewById(R.id.btnVolverLogin);

        databaseHelper = new DatabaseHelper(this);

        btnCrearCuenta.setOnClickListener(v -> registrarUsuario());

        btnVolverLogin.setOnClickListener(v -> finish());

    }

    private void registrarUsuario() {

        String nombre = txtNombre.getText().toString().trim();
        String correo = txtCorreo.getText().toString().trim();
        String password = txtPassword.getText().toString();
        String confirmar = txtConfirmarPassword.getText().toString();

        if (nombre.isEmpty() ||
                correo.isEmpty() ||
                password.isEmpty() ||
                confirmar.isEmpty()) {

            Toast.makeText(
                    this,
                    "Complete todos los campos.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {

            Toast.makeText(
                    this,
                    "Correo electrónico inválido.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (!password.equals(confirmar)) {

            Toast.makeText(
                    this,
                    "Las contraseñas no coinciden.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (databaseHelper.existeCorreo(correo)) {

            Toast.makeText(
                    this,
                    "Ese correo ya está registrado.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String fecha = new SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
        ).format(new Date());

        boolean registrado = databaseHelper.registrarUsuario(
                nombre,
                correo,
                password,
                fecha,
                "Activo"
        );

        if (registrado) {

            Toast.makeText(
                    this,
                    "Usuario registrado correctamente.",
                    Toast.LENGTH_LONG
            ).show();

            startActivity(
                    new Intent(
                            RegistroUsuarioActivity.this,
                            LoginActivity.class
                    )
            );

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Error al registrar el usuario.",
                    Toast.LENGTH_SHORT
            ).show();

        }

    }

}
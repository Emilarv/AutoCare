package com.autocare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.activities.DashboardActivity;
import com.autocare.activities.LoginActivity;
import com.autocare.activities.RegistroUsuarioActivity;
import com.autocare.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();

        preferencias = getSharedPreferences(
                "AutoCarePrefs",
                MODE_PRIVATE
        );

        Log.d(
                "AUTOCARE_DB",
                "Base de datos abierta correctamente"
        );

        boolean sesionIniciada = preferencias.getBoolean(
                "sesionIniciada",
                false
        );

        Intent intent;

        if (sesionIniciada) {

            intent = new Intent(
                    MainActivity.this,
                    DashboardActivity.class
            );

        } else if (databaseHelper.existeAlgunUsuario()) {

            intent = new Intent(
                    MainActivity.this,
                    LoginActivity.class
            );

        } else {

            intent = new Intent(
                    MainActivity.this,
                    RegistroUsuarioActivity.class
            );

        }

        startActivity(intent);
        finish();
    }
}
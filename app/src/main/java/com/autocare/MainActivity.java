package com.autocare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.activities.LoginActivity;
import com.autocare.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();

        Log.d("AUTOCARE_DB", "Base de datos abierta correctamente");

        Toast.makeText(
                this,
                "Base de datos abierta correctamente",
                Toast.LENGTH_LONG
        ).show();

        Intent intent = new Intent(
                MainActivity.this,
                LoginActivity.class
        );

        startActivity(intent);
        finish();
    }
}
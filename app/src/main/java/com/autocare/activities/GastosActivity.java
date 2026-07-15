package com.autocare.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.autocare.R;
import com.autocare.database.DatabaseHelper;
import com.autocare.models.Gasto;

import java.util.ArrayList;

public class GastosActivity extends AppCompatActivity {

    private TextView txtTitulo;
    private TextView txtMonto;
    private TextView txtCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtMonto = findViewById(R.id.txtMonto);
        txtCategoria = findViewById(R.id.txtCategoria);

        DatabaseHelper db = new DatabaseHelper(this);

        boolean historialGeneral =
                getIntent().getBooleanExtra("historialGeneral", false);

        if(historialGeneral){

            txtTitulo.setText("Historial General de Gastos");

            ArrayList<Gasto> lista = db.obtenerTodosLosGastos();

            double total = 0;

            StringBuilder detalle = new StringBuilder();

            for(Gasto g : lista){

                total += g.getMonto();

                detalle.append("• ")
                        .append(g.getCategoria())
                        .append(" - RD$")
                        .append(String.format("%.2f", g.getMonto()))
                        .append("\n");

            }

            txtMonto.setText("RD$ " + String.format("%.2f", total));

            if(detalle.length()==0){

                txtCategoria.setText("No hay gastos registrados");

            }else{

                txtCategoria.setText(detalle.toString());

            }

        }else{

            int idMantenimiento =
                    getIntent().getIntExtra("idMantenimiento",-1);

            String tipo =
                    getIntent().getStringExtra("tipo");

            txtTitulo.setText(tipo);

            ArrayList<Gasto> lista =
                    db.obtenerGastosPorMantenimiento(idMantenimiento);

            if(lista.size()>0){

                Gasto gasto = lista.get(0);

                txtMonto.setText("RD$ " + gasto.getMonto());

                txtCategoria.setText(gasto.getCategoria());

            }else{

                txtMonto.setText("RD$0");

                txtCategoria.setText("Sin gastos registrados");

            }

        }

    }
}
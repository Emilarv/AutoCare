package com.autocare.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autocare.R;
import com.autocare.activities.GastosActivity;
import com.autocare.models.Mantenimiento;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MantenimientoAdapter extends RecyclerView.Adapter<MantenimientoAdapter.ViewHolder> {

    private ArrayList<Mantenimiento> lista;

    public MantenimientoAdapter(ArrayList<Mantenimiento> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mantenimiento, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Mantenimiento mantenimiento = lista.get(position);

        holder.txtTipo.setText(mantenimiento.getNombreTipo());

        holder.txtFecha.setText(
                "Fecha: " + mantenimiento.getFecha()
        );

        holder.txtKilometraje.setText(
                mantenimiento.getKilometraje() + " km"
        );

        // Si descripcion está vacía mostramos solo el tipo.
        // Si contiene el nombre del vehículo (historial general)
        // lo mostramos debajo.
        if (mantenimiento.getDescripcion() != null &&
                !mantenimiento.getDescripcion().trim().isEmpty()) {

            holder.txtDescripcion.setText(
                    mantenimiento.getDescripcion()
            );

        } else {

            holder.txtDescripcion.setText("");

        }

        holder.btnVerGastos.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    GastosActivity.class
            );

            intent.putExtra(
                    "idMantenimiento",
                    mantenimiento.getIdMantenimiento()
            );

            intent.putExtra(
                    "tipo",
                    mantenimiento.getNombreTipo()
            );

            v.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTipo;
        TextView txtFecha;
        TextView txtKilometraje;
        TextView txtDescripcion;

        MaterialButton btnVerGastos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTipo = itemView.findViewById(R.id.txtTipo);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtKilometraje = itemView.findViewById(R.id.txtKilometraje);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);

            btnVerGastos = itemView.findViewById(R.id.btnVerGastos);
        }
    }
}
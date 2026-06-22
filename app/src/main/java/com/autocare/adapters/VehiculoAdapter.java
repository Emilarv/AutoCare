package com.autocare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autocare.R;
import com.autocare.models.Vehiculo;

import java.util.ArrayList;

public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.ViewHolder> {

    private ArrayList<Vehiculo> listaVehiculos;

    private OnVehiculoClickListener listener;

    public interface OnVehiculoClickListener {

        void onEditarClick(Vehiculo vehiculo);

        void onEliminarClick(Vehiculo vehiculo);

    }

    public VehiculoAdapter(
            ArrayList<Vehiculo> listaVehiculos,
            OnVehiculoClickListener listener) {

        this.listaVehiculos = listaVehiculos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_vehiculo,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Vehiculo vehiculo = listaVehiculos.get(position);

        holder.txtNombreVehiculo.setText(
                vehiculo.getMarca() + " " +
                        vehiculo.getModelo()
        );

        holder.txtInfoVehiculo.setText(
                "Año: " +
                        vehiculo.getAnio() +
                        " • Placa: " +
                        vehiculo.getPlaca()
        );

        holder.btnEditar.setOnClickListener(v ->
                listener.onEditarClick(vehiculo)
        );

        holder.btnEliminar.setOnClickListener(v ->
                listener.onEliminarClick(vehiculo)
        );
    }

    @Override
    public int getItemCount() {

        return listaVehiculos.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombreVehiculo;
        TextView txtInfoVehiculo;

        Button btnEditar;
        Button btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombreVehiculo =
                    itemView.findViewById(
                            R.id.txtNombreVehiculo);

            txtInfoVehiculo =
                    itemView.findViewById(
                            R.id.txtInfoVehiculo);

            btnEditar =
                    itemView.findViewById(
                            R.id.btnEditar);

            btnEliminar =
                    itemView.findViewById(
                            R.id.btnEliminar);
        }
    }
}
package com.example.integrasalud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PacienteRVAdapter extends RecyclerView.Adapter<PacienteRVAdapter.ViewHolder>
{

    // creating variables for our list, context, interface and position.
    private ArrayList<PacienteRVModal> pacienteRVModalArrayList;
    private Context context;
    private PacienteClickInterface pacienteClickInterface;
    int lastPos = -1;

    // creating a constructor.
    public PacienteRVAdapter(ArrayList<PacienteRVModal> pacienteRVModalArrayList, Context context, PacienteClickInterface pacienteClickInterface) {
        this.pacienteRVModalArrayList = pacienteRVModalArrayList;
        this.context = context;
        this.pacienteClickInterface = pacienteClickInterface;
    }

    @NonNull
    @Override
    public PacienteRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.paciente_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PacienteRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // setting data to our recycler view item on below line.
        PacienteRVModal pacienteRVModal = pacienteRVModalArrayList.get(position);
        holder.pacienteTV.setText(pacienteRVModal.getNombre());
        holder.pacienteEdadTV.setText("Rs. " + pacienteRVModal.getApellidos());
        Picasso.get().load(pacienteRVModal.getEdad()).into(holder.pacienteIV);
        // adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position);
        holder.pacienteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pacienteClickInterface.onPacienteClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return pacienteRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.
        private ImageView pacienteIV;
        private TextView pacienteTV, pacienteEdadTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line.
            pacienteIV = itemView.findViewById(R.id.idIVPaciente);
            pacienteTV = itemView.findViewById(R.id.idTVNombre);
            pacienteEdadTV = itemView.findViewById(R.id.idTVEdad);
        }
    }

    // creating a interface for on click
    public interface PacienteClickInterface {
        void onPacienteClick(int position);
    }
}
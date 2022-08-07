package com.example.integrasalud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AgregarDatosActivity extends AppCompatActivity {

    // creating variables for our button, edit text,
    // firebase database, database refrence, progress bar.
    private Button addDatos;
    private TextInputEditText oxiEdt, ritmoEdt, caloriasEdt, distanciaEdt, pasosEdt, idEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_datos);
        // initializing all our variables.
        addDatos = findViewById(R.id.idBtnAddPaciente);
        idEdt = findViewById(R.id.idTILDatos);
        oxiEdt = findViewById(R.id.idTILOxi);
        ritmoEdt = findViewById(R.id.idTILRitmo);
        caloriasEdt = findViewById(R.id.idTILCalorias);
        distanciaEdt = findViewById(R.id.idTILDistancia);
        pasosEdt = findViewById(R.id.idTILPasos);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Datos");
        // adding click listener for our add course button.
        addDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                // getting data from our edit text.
                String id = idEdt.getText().toString();
                String oxi = oxiEdt.getText().toString();
                String ritmo = ritmoEdt.getText().toString();
                String calorias = caloriasEdt.getText().toString();
                String distancia = distanciaEdt.getText().toString();
                String pasos = pasosEdt.getText().toString();
                //String id = id.getText().toString();
                //courseID = courseName;
                // on below line we are passing all data to our modal class.
                DatosRVModal datosRVModal = new DatosRVModal(id, oxi, ritmo, calorias, distancia, pasos);
                // on below line we are calling a add value event
                // to pass data to firebase database.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // on below line we are setting data in our firebase database.
                        databaseReference.child(id).setValue(datosRVModal);
                        // displaying a toast message.
                        Toast.makeText(AgregarDatosActivity.this, "Datos Agregados..", Toast.LENGTH_SHORT).show();
                        // starting a main activity.
                        startActivity(new Intent(AgregarDatosActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on below line.
                        Toast.makeText(AgregarDatosActivity.this, "Error en Agregar Datos..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
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

public class AgregarPacienteActivity extends AppCompatActivity {

    // creating variables for our button, edit text,
    // firebase database, database refrence, progress bar.
    private Button addPaciente;
    private TextInputEditText nombreEdt, apellidosEdt, edadEdt, clinicaEdt, pesoEdt, alturaEdt, actividadEdt, idEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_paciente);
        // initializing all our variables.
        addPaciente = findViewById(R.id.idBtnAddPaciente);
        idEdt = findViewById(R.id.idEdtId);
        nombreEdt = findViewById(R.id.idEdtNombre);
        apellidosEdt = findViewById(R.id.idEdtApellidos);
        edadEdt = findViewById(R.id.idEdtEdad);
        clinicaEdt = findViewById(R.id.idEdtClinica);
        pesoEdt = findViewById(R.id.idEdtPeso);
        alturaEdt = findViewById(R.id.idEdtPeso);
        actividadEdt = findViewById(R.id.idEdtPeso);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Pacientes");
        // adding click listener for our add course button.
        addPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                // getting data from our edit text.
                String id = idEdt.getText().toString();
                String nombre = nombreEdt.getText().toString();
                String apellidos = apellidosEdt.getText().toString();
                String edad = edadEdt.getText().toString();
                String clinica = clinicaEdt.getText().toString();
                String peso = pesoEdt.getText().toString();
                String altura = alturaEdt.getText().toString();
                String actividad = actividadEdt.getText().toString();
                //String id = id.getText().toString();
                //courseID = courseName;
                // on below line we are passing all data to our modal class.
                PacienteRVModal pacienteRVModal = new PacienteRVModal(id, nombre, apellidos, edad, clinica, peso, altura, actividad);
                // on below line we are calling a add value event
                // to pass data to firebase database.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // on below line we are setting data in our firebase database.
                        databaseReference.child(id).setValue(pacienteRVModal);
                        // displaying a toast message.
                        Toast.makeText(AgregarPacienteActivity.this, "Paciente Agregado..", Toast.LENGTH_SHORT).show();
                        // starting a main activity.
                        startActivity(new Intent(AgregarPacienteActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on below line.
                        Toast.makeText(AgregarPacienteActivity.this, "Error en Agregar Paciente..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
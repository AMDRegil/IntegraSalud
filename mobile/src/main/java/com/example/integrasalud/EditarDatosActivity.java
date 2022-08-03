package com.example.integrasalud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditarDatosActivity extends AppCompatActivity {

    // creating variables for our edit text, firebase database,
    // database reference, course rv modal,progress bar.
    private TextInputEditText idEdt, nombreEdt, apellidosEdt, edadEdt, clinicaEdt, pesoEdt, alturaEdt, actividadEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    PacienteRVModal pacienteRVModal;
    private ProgressBar loadingPB;
    // creating a string for our course id.
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_paciente);
        // initializing all our variables on below line.
        Button addPacienteBtn = findViewById(R.id.idBtnAddPaciente);
        //id = findViewById(R.id.idEdtId);
        nombreEdt = findViewById(R.id.idEdtNombre);
        apellidosEdt = findViewById(R.id.idEdtApellidos);
        edadEdt = findViewById(R.id.idEdtEdad);
        clinicaEdt = findViewById(R.id.idEdtClinica);
        pesoEdt = findViewById(R.id.idEdtPeso);
        alturaEdt = findViewById(R.id.idEdtAltura);
        actividadEdt = findViewById(R.id.idEdtActividad);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        pacienteRVModal = getIntent().getParcelableExtra("paciente");
        Button deletePacienteBtn = findViewById(R.id.idBtnDeletePaciente);

        if (pacienteRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            nombreEdt.setText(pacienteRVModal.getNombre());
            apellidosEdt.setText(pacienteRVModal.getApellidos());
            edadEdt.setText(pacienteRVModal.getEdad());
            clinicaEdt.setText(pacienteRVModal.getClinica());
            pesoEdt.setText(pacienteRVModal.getPeso());
            alturaEdt.setText(pacienteRVModal.getAltura());
            actividadEdt.setText(pacienteRVModal.getActividad());
            id = pacienteRVModal.getId();
        }

        // on below line we are initialing our database reference and we are adding a child as our course id.
        databaseReference = firebaseDatabase.getReference("Pacientes").child(id);
        // on below line we are adding click listener for our add course button.
        addPacienteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are making our progress bar as visible.
                loadingPB.setVisibility(View.VISIBLE);
                // on below line we are getting data from our edit text.
                String nombre = nombreEdt.getText().toString();
                String apellidos = apellidosEdt.getText().toString();
                String edad = edadEdt.getText().toString();
                String clinica = clinicaEdt.getText().toString();
                String peso = pesoEdt.getText().toString();
                String altura = alturaEdt.getText().toString();
                String actividad = actividadEdt.getText().toString();
                // on below line we are creating a map for
                // passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("nombre", nombre);
                map.put("apellidos", apellidos);
                map.put("edad", edad);
                map.put("clinica", clinica);
                map.put("peso", peso);
                map.put("altura", altura);
                map.put("actividad", actividad);
                map.put("id", id);

                // on below line we are calling a database reference on
                // add value event listener and on data change method
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // making progress bar visibility as gone.
                        loadingPB.setVisibility(View.GONE);
                        // adding a map to our database.
                        databaseReference.updateChildren(map);
                        // on below line we are displaying a toast message.
                        Toast.makeText(EditarPacienteActivity.this, "Paciente Actualizado..", Toast.LENGTH_SHORT).show();
                        // opening a new activity after updating our coarse.
                        startActivity(new Intent(EditarPacienteActivity.this, MainActivity2.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on toast.
                        Toast.makeText(EditarPacienteActivity.this, "Error en Actualizar Paciente..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // adding a click listener for our delete course button.
        deletePacienteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete a course.
                deletePaciente();
            }
        });

    }

    private void deletePaciente() {
        // on below line calling a method to delete the course.
        databaseReference.removeValue();
        // displaying a toast message on below line.
        Toast.makeText(this, "Paciente Eliminado..", Toast.LENGTH_SHORT).show();
        // opening a main activity on below line.
        startActivity(new Intent(EditarPacienteActivity.this, MainActivity2.class));
    }
}
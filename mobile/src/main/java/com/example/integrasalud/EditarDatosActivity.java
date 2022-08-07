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
    private TextInputEditText idEdtDatos, oxiEdt, ritmoEdt, caloriasEdt, distanciaEdt, pasosEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatosRVModal datosRVModal;
    private ProgressBar loadingPB;
    // creating a string for our course id.
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_datos);
        // initializing all our variables on below line.
        Button addDatosBtn = findViewById(R.id.idBtnAddDatos);
        //id = findViewById(R.id.idEdtId);
        oxiEdt = findViewById(R.id.idEdtOxi);
        ritmoEdt = findViewById(R.id.idEdtRitmo);
        caloriasEdt = findViewById(R.id.idEdtCalorias);
        distanciaEdt = findViewById(R.id.idEdtDistancia);
        pasosEdt = findViewById(R.id.idEdtPasos);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        datosRVModal = getIntent().getParcelableExtra("datos");
        Button deleteDatosBtn = findViewById(R.id.idBtnDeleteDatos);

        if (datosRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            oxiEdt.setText(datosRVModal.getOxi());
            ritmoEdt.setText(datosRVModal.getRitmo());
            caloriasEdt.setText(datosRVModal.getCalorias());
            distanciaEdt.setText(datosRVModal.getDistancia());
            pasosEdt.setText(datosRVModal.getPasos());
            id = datosRVModal.getId();
        }

        // on below line we are initialing our database reference and we are adding a child as our course id.
        databaseReference = firebaseDatabase.getReference("Datos").child(id);
        // on below line we are adding click listener for our add course button.
        addDatosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are making our progress bar as visible.
                loadingPB.setVisibility(View.VISIBLE);
                // on below line we are getting data from our edit text.
                String oxi = oxiEdt.getText().toString();
                String ritmo = ritmoEdt.getText().toString();
                String calorias = caloriasEdt.getText().toString();
                String distancia = distanciaEdt.getText().toString();
                String pasos = pasosEdt.getText().toString();
                // on below line we are creating a map for
                // passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("oxi", oxi);
                map.put("ritmo", ritmo);
                map.put("calorias", calorias);
                map.put("distancia", distancia);
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
                        Toast.makeText(EditarDatosActivity.this, "Datos Actualizados..", Toast.LENGTH_SHORT).show();
                        // opening a new activity after updating our coarse.
                        startActivity(new Intent(EditarDatosActivity.this, MainActivity2.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on toast.
                        Toast.makeText(EditarDatosActivity.this, "Error en Actualizar Datos..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // adding a click listener for our delete course button.
        deleteDatosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete a course.
                deleteDatos();
            }
        });

    }

    private void deleteDatos() {
        // on below line calling a method to delete the course.
        databaseReference.removeValue();
        // displaying a toast message on below line.
        Toast.makeText(this, "Datos Eliminados..", Toast.LENGTH_SHORT).show();
        // opening a main activity on below line.
        startActivity(new Intent(EditarDatosActivity.this, MainActivity2.class));
    }
}
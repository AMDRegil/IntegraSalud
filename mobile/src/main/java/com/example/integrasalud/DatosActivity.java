package com.example.integrasalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatosActivity extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
    }*/
    /*

    public String ID;
    public String pasos;
    public String distancia;
    public String calorias;
    public String ritmo;
    public String oxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID = findViewById(R.id.DatosInputID);
        txtOxi = findViewById(R.id.DatosInputOxi);
        txtRitmo = findViewById(R.id.DatosInputRitmo);
        txtCalorias = findViewById(R.id.DatosInputCalorias);
        txtDistancia = findViewById(R.id.DatosInputDistancia);
        txtPasos = findViewById(R.id.DatosInputPasos);

        btnSave = findViewById(R.id.btnSave);
        btnShow = findViewById(R.id.btnShow);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        datos = new Datos();
    }

    public static class Datos {

        private String ID;
        private String oxi;
        private String ritmo;
        private String calorias;
        private String distancia;
        private String pasos;

        public String getID() {
            return ID;
        }

        public String getOxi() {
            return oxi;
        }

        public String getRitmo() {
            return ritmo;
        }

        public String getCalorias() {
            return calorias;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public void setOxi(String oxi) {
            this.oxi = oxi;
        }

        public void setEdad(String edad) {
            this.edad = edad;
        }

        public void setClinica(String clinica) {
            this.clinica = clinica;
        }
    }

    EditText txtID, txtNombre, txtEdad, txtClinica;
    Button btnSave, btnShow, btnUpdate, btnDelete;
    DatabaseReference dbRef;
    Paciente pac;

    public void createPaciente() {
        dbRef = FirebaseDatabase.getInstance().getReference().child("Pacientes");
        try {
            if (TextUtils.isEmpty(txtID.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Ingrese el ID del Paciente", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(txtNombre.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Ingrese el Nombre Completo", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(txtEdad.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Ingrese la Edad", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(txtClinica.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Ingrese la Clinica", Toast.LENGTH_SHORT).show();
            } else {
                pac.setID(txtID.getText().toString().trim());
                pac.setNombre(txtNombre.getText().toString().trim());
                pac.setEdad(txtEdad.getText().toString().trim());
                pac.setClinica(txtClinica.getText().toString().trim());

                //Insertar detalles del paciente a la BD
                dbRef.push().setValue(pac);

                //Mensaje Popup
                Toast.makeText(getApplicationContext(), "Datos agregados correctamente", Toast.LENGTH_SHORT).show();
            }
        } catch(NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Datos inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void showPaciente() {
        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Pacientes").child("pac1");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (dataSnapshot.hasChildren()) {
                    txtID.setText(dataSnapshot.child("id").getValue().toString());
                    txtNombre.setText(dataSnapshot.child("nombre").getValue().toString());
                    txtEdad.setText(dataSnapshot.child("edad").getValue().toString());
                    txtClinica.setText(dataSnapshot.child("clinica").getValue().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "No hay datos para mostrar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    } */
    /*

    public void updatePaciente() {
        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Paciente");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (dataSnapshot.hasChild("pac1")) {
                    try{
                        pac.setID(txtID.getText().toString().trim());
                        pac.setNombre(txtNombre.getText().toString().trim());
                        pac.setEdad(Integer.parseInt(txtEdad.getText().toString().trim()));
                        pac.setClinica(txtClinica.getText().toString().trim());

                        dbRef = FirebaseDatabase.getInstance().getReference().child("Paciente").child("pac1");
                        dbRef.setValue(pac);

                        //Muestra mensaje popup para actualización exitosa
                        Toast.makeText(getApplicationContext(), "Datos Actualizados Correctamente", Toast.LENGTH_SHORT).show();
                    } catch(NumberFormatException) {
                        Toast.makeText(getApplicationContext(), "Datos inválidos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No hay datos para actualizar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); */
   // }
    /*

    public void deletePaciente() {
        DatabaseReference deletePac = FirebaseDatabase.getInstance().getReference().child("Paciente");
        deletePac.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Paciente").child("pac1");
                dbRef.removeValue();
                Toast.makeText(getApplicationContext(), "Datos eliminados", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    } */
}
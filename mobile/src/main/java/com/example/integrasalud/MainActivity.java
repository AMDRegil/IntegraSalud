package com.example.integrasalud;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.example.integrasalud.model.Actividad;

public class MainActivity extends AppCompatActivity {

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Animaciones
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        TextView byTextView = findViewById(R.id.byTextView);
        final TextView integraTextView = findViewById(R.id.integraTextView);
        final ImageView logoImageView = findViewById(R.id.logoImageView);

        byTextView.setAnimation(animacion2);
        integraTextView.setAnimation(animacion2);
        logoImageView.setAnimation(animacion1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                    Pair[] pairs = new Pair[2];
                    pairs [0] = Pair.create(logoImageView, "logoImageTrans");
                    pairs [1] = Pair.create(integraTextView, "textTrans");
                    pairs [0] = new android.util.Pair(logoImageView, "logoImageTrans");
                    pairs [1] = new android.util.Pair(integraTextView, "textTrans");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, 4000);
    }*/

    private List<Actividad> listActividad = new ArrayList<Actividad>();
    ArrayAdapter<Actividad> arrayAdapterActividad;

    EditText idP, nombreP, correoP, contrasenaP, oxiP, ritmoP, caloriasP, distanciaP, pasosP;
    ListView listV_actividades;
    TextView agua, cantidad;
    Button talkButton;

    protected Handler myHandler;
    int receivedMessageNumber = 1;
    int sentMessageNumber = 1;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Actividad actividadSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idP = findViewById(R.id.txt_idPaciente);
        nombreP = findViewById(R.id.txt_nombrePaciente);
        correoP = findViewById(R.id.txt_correoPaciente);
        contrasenaP = findViewById(R.id.txt_contrasenaPaciente);
        oxiP = findViewById(R.id.txt_oxiPaciente);
        ritmoP = findViewById(R.id.txt_ritmoPaciente);
        caloriasP = findViewById(R.id.txt_caloriasPaciente);
        distanciaP = findViewById(R.id.txt_distanciaPaciente);
        pasosP = findViewById(R.id.txt_pasosPaciente);
        agua = findViewById(R.id.txt_aguaPaciente);
        cantidad = findViewById(R.id.cantidad);
        talkButton = findViewById(R.id.talkButton);

        listV_actividades = findViewById(R.id.lv_datosActividades);
        inicializarFirebase();
        listarDatos();

        listV_actividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actividadSelected = (Actividad) parent.getItemAtPosition(position);
                idP.setText(actividadSelected.getIdPaciente());
                nombreP.setText(actividadSelected.getNombre());
                correoP.setText(actividadSelected.getCorreo());
                contrasenaP.setText(actividadSelected.getContrasena());
                oxiP.setText(actividadSelected.getOxi());
                ritmoP.setText(actividadSelected.getRitmo());
                caloriasP.setText(actividadSelected.getCalorias());
                distanciaP.setText(actividadSelected.getDistancia());
                pasosP.setText(actividadSelected.getPasos());
            }
        });

        //Mensajes
        //Controlador de mensajes
        myHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle stuff = msg.getData();
                messageText(stuff.getString("messageText"));
                return true;
            }
        });

        //Registro de recepción de transmisiones
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
    }

    public void messageText(String newinfo) {
        if (newinfo.compareTo("") != 0) {
            agua.append("\n" + newinfo);
        }
    }

    //Definir clase que extiende a BroadcastReceiver
    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            //Al recibir un mensaje del wearable, muestra lo siguiente
            String message = "500ml" + receivedMessageNumber++;;
            agua.setText(message);
        }
    }

    public void talkClick(View v) {
        String message = "Enviando mensaje...";
        agua.setText(message);

        //Enviar un mensaje puede bloquear la UI principal, se usa un nuevo hilo
        new NewThread("/my_path", message).start();
    }

    //Usar un paquete para encapsular el mensaje
    public void sendmessage(String messageText) {
        Bundle bundle = new Bundle();
        bundle.putString("messageText", messageText);
        Message msg = myHandler.obtainMessage();
        msg.setData(bundle);
        myHandler.sendMessage(msg);
    }

    class NewThread extends Thread {
        String path;
        String message;

        //Constructor para enviar información a la Capa de Datos
        NewThread(String p, String m) {
            path = p;
            message = m;
        }

        public void run() {

            //Obtiene los dispositivos o nodos conectados
            Task<List<Node>> wearableList =
                    Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
            try {
                List<Node> nodes = Tasks.await(wearableList);
                for (Node node : nodes) {
                    Task<Integer> sendMessageTask =
                            //Envía el mensaje
                            Wearable.getMessageClient(MainActivity.this).sendMessage(node.getId(), path, message.getBytes());
                    try {
                        //Bloquear la tarea y obtener un resultado síncrono
                        Integer result = Tasks.await(sendMessageTask);
                        sendmessage("I just sent the wearable a message " + sentMessageNumber++);
                        //Si la tarea falla
                    } catch (ExecutionException exception) {
                        //Hace: Maneja la excepción
                    } catch (InterruptedException exception) {
                        //Hace: Maneja la excepción
                    }
                }
            } catch (ExecutionException exception) {
                //Hace: Maneja la excepción
            } catch (InterruptedException exception) {
                //Hace: Maneja la excecpión
            }
        }
    }

    private void listarDatos() {
        databaseReference.child("Actividad").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listActividad.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Actividad p = objSnaptshot.getValue(Actividad.class);
                    listActividad.add(p);

                    arrayAdapterActividad = new ArrayAdapter<Actividad>(MainActivity.this, android.R.layout.simple_list_item_1, listActividad);
                    listV_actividades.setAdapter(arrayAdapterActividad);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String idPaciente = idP.getText().toString();
        String nombre = nombreP.getText().toString();
        String correo = correoP.getText().toString();
        String contrasena = contrasenaP.getText().toString();
        String oxi = oxiP.getText().toString();
        String ritmo = ritmoP.getText().toString();
        String calorias = caloriasP.getText().toString();
        String distancia = distanciaP.getText().toString();
        String pasos = pasosP.getText().toString();
        //String app = appP.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add:{
                if (idPaciente.equals("")||nombre.equals("")||correo.equals("")||contrasena.equals("")||oxi.equals("")||ritmo.equals("")||calorias.equals("")||distancia.equals("")||pasos.equals("")){
                    validacion();
                }
                else {
                    Actividad p = new Actividad();
                    p.setUid(UUID.randomUUID().toString());
                    p.setIdPaciente(idPaciente);
                    p.setNombre(nombre);
                    //p.setApellido(app);
                    p.setCorreo(correo);
                    p.setContrasena(contrasena);
                    p.setOxi(oxi);
                    p.setRitmo(ritmo);
                    p.setCalorias(calorias);
                    p.setDistancia(distancia);
                    p.setPasos(pasos);
                    databaseReference.child("Actividad").child(p.getUid()).setValue(p);
                    Toast.makeText(this, "Agregada", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }
                break;
            }
            case R.id.icon_save:{
                Actividad p = new Actividad();
                p.setUid(actividadSelected.getUid());
                p.setIdPaciente(idP.getText().toString().trim());
                p.setNombre(nombreP.getText().toString().trim());
                //p.setApellido(appP.getText().toString().trim());
                p.setCorreo(correoP.getText().toString().trim());
                p.setContrasena(contrasenaP.getText().toString().trim());
                p.setOxi(oxiP.getText().toString().trim());
                p.setRitmo(ritmoP.getText().toString().trim());
                p.setCalorias(caloriasP.getText().toString().trim());
                p.setDistancia(distanciaP.getText().toString().trim());
                p.setPasos(pasosP.getText().toString().trim());
                databaseReference.child("Actividad").child(p.getUid()).setValue(p);
                Toast.makeText(this,"Actualizada", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_delete:{
                Actividad p = new Actividad();
                p.setUid(actividadSelected.getUid());
                databaseReference.child("Actividad").child(p.getUid()).removeValue();
                Toast.makeText(this,"Eliminada", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            default:break;
        }
        return true;
    }

    private void limpiarCajas() {
        idP.setText("");
        nombreP.setText("");
        correoP.setText("");
        contrasenaP.setText("");
        oxiP.setText("");
        ritmoP.setText("");
        caloriasP.setText("");
        distanciaP.setText("");
        pasosP.setText("");
    }

    private void validacion() {
        String idPaciente = idP.getText().toString();
        String nombre = nombreP.getText().toString();
        String correo = correoP.getText().toString();
        String contrasena = contrasenaP.getText().toString();
        String oxi = oxiP.getText().toString();
        String ritmo = ritmoP.getText().toString();
        String calorias = caloriasP.getText().toString();
        String distancia = distanciaP.getText().toString();
        String pasos = pasosP.getText().toString();
        if (nombre.equals("")){
            nombreP.setError("Required");
        }
        else if (idPaciente.equals("")){
            idP.setError("Required");
        }
        else if (correo.equals("")){
            correoP.setError("Required");
        }
        else if (contrasena.equals("")){
            contrasenaP.setError("Required");
        }
        else if (oxi.equals("")){
            oxiP.setError("Required");
        }
        else if (ritmo.equals("")){
            ritmoP.setError("Required");
        }
        else if (calorias.equals("")){
            caloriasP.setError("Required");
        }
        else if (distancia.equals("")){
            distanciaP.setError("Required");
        }
        else if (pasos.equals("")){
            pasosP.setError("Required");
        }
    }

}
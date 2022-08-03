package com.example.integrasalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MensajeActivity extends AppCompatActivity {

    Button talkbutton;
    TextView agua;
    protected Handler myHandler;
    int receivedMessageNumber = 1;
    int sentMessageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        talkbutton = findViewById(R.id.talkButton);
        agua = findViewById(R.id.agua);

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
                            Wearable.getMessageClient(MensajeActivity.this).sendMessage(node.getId(), path, message.getBytes());
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
}
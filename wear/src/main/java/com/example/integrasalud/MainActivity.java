package com.example.integrasalud;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.integrasalud.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    private TextView ml;
    Button sumaButton;
    int receivedMessageNumber = 1;
    int sentMessageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ml = findViewById(R.id.ml);
        sumaButton = findViewById(R.id.sumaButton);

        //Crear un OnClickListener
        sumaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String onClickMessage = "500ml" + sentMessageNumber++;
                ml.setText(onClickMessage);

                //Usar la misma ruta o path
                String datapath = "/my_path";
                new SendMessage(datapath, onClickMessage).start();
            }
        });

        //Registrar para recibir transmisiones locales
        IntentFilter newFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, newFilter);
    }

    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Muestra lo siguiente cuando recibe un mensaje
            String onMessageReceived = "500ml" + receivedMessageNumber++;
            ml.setText(onMessageReceived);
        }
    }
    class SendMessage extends Thread {
        String path;
        String message;

        //Constructor para enviar información a la capa de datos
        SendMessage (String p, String m) {
            path = p;
            message = m;
        }

        public void run() {

            //Recupera los dispositivos conectados
            Task<List<Node>> nodeListTask =
                    Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
            try {
                //Bloquea la tarea y obtiene el resultado síncrono
                List<Node> nodes = Tasks.await(nodeListTask);
                for (Node node : nodes) {
                    //Envía el mensaje
                    Task<Integer> sendMessageTask =
                            Wearable.getMessageClient(MainActivity.this).sendMessage(node.getId(), path, message.getBytes());
                    try {
                        Integer result = Tasks.await(sendMessageTask);
                        //Maneja los errores
                    } catch (ExecutionException exception) {
                        //TO DO//
                    } catch (InterruptedException exception) {
                        //TO DO//
                    }
                }
            } catch (ExecutionException exception) {
                //TO DO//
            } catch (InterruptedException exception) {
                //TO DO//
            }
        }
    }
}
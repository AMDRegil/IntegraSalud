package com.example.integrasalud;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.content.Intent;
//import android.support.v4.content.LocalBroadcastManager;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class MessageService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        //Si la ruta es equivalente
        if(messageEvent.getPath().equals("/my_path")) {

            //Recupera el mensaje
            final String message = new String(messageEvent.getData());

            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("message", message);

            //Envía el mensaje recibido localmente
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}
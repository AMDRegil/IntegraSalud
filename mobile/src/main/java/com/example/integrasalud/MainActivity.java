package com.example.integrasalud;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*//Animaciones
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
        }, 4000);*/
    }

}
package com.example.tfg4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.up);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.down);

        ImageView imgLogo = findViewById(R.id.imgLogo);
        TextView txtPoliMAD = findViewById(R.id.txtPoliMAD);
        TextView txtSlogan = findViewById(R.id.txtSlogan);

        txtPoliMAD.setAnimation(animation2);
        txtSlogan.setAnimation(animation2);
        imgLogo.setAnimation(animation1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Start.this, Login.class);
                startActivity(intent);
            }
        }, 4000);
    }
}
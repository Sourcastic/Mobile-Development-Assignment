package com.example.l230759ass1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Context context = getApplicationContext();
        CharSequence text = "Welcome to CineFast";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();


        ImageView logo = findViewById(R.id.logo);
        Animation spin = AnimationUtils.loadAnimation(this, R.anim.logo_spin);
        logo.startAnimation(spin);


        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(Splash.this, Onboarding.class));
            finish();
        }, 5000);
    }
}

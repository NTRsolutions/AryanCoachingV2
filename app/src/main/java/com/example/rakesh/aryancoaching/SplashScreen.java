package com.example.rakesh.aryancoaching;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SplashScreen extends AppCompatActivity {

    private final int Splash_Display_length = 3000;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Main_Screen = new Intent(SplashScreen.this,MainActivity.class);
                SplashScreen.this.startActivity(Main_Screen);
                SplashScreen.this.finish();
            }
        },Splash_Display_length);
    }
}

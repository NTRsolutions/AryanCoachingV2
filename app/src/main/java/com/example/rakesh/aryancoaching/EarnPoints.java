package com.example.rakesh.aryancoaching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class EarnPoints extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_points);


        Button fivepointsbtn = findViewById(R.id.fivePoints);
        fivepointsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(EarnPoints.this, "In Order To Claim Your Points Visit Advertiser", Toast.LENGTH_SHORT);

            }
        });

        Button tenpoints = findViewById(R.id.tenPoints);
        tenpoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(EarnPoints.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT);
            }
        });

    }


}

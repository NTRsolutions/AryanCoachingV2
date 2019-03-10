package com.example.rakesh.aryancoaching;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class HomeTutionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tution);

        Button registration = (Button)findViewById(R.id.registration_button);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(HomeTutionActivity.this);
                adb.setMessage(" Registration Charges Rs.500/registration ");
                adb.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent Home_tution_Activity = new Intent(HomeTutionActivity.this,RegistrationTutor.class);
                        startActivity(Home_tution_Activity);
                    }
                });
                adb.setNegativeButton("Cancel", null);
                adb.show();

            }
        });
        Button homeTutEnquiry = findViewById(R.id.hometution_enquiry_button);
        homeTutEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(HomeTutionActivity.this,Enquiry_Details.class);
                startActivity(newIntent);
            }
        });
        Button ViewTut = findViewById(R.id.availabel_teachers_button);
        ViewTut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent1 = new Intent(HomeTutionActivity.this,FacultyActivity.class);
                startActivity(newIntent1);
            }
        });

    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();

    }
}

package com.example.rakesh.aryancoaching;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Enquiry_Details extends AppCompatActivity {
    String Details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry__details);




        Button Submit_Button  = (Button)findViewById(R.id.submit_button);
        Submit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText StudentName = findViewById(R.id.student_name);
                EditText StudentClass = findViewById(R.id.student_Class);
                EditText StudentContact = findViewById(R.id.student_contact_no);
                EditText StudentSubject = findViewById(R.id.student_subject);
               final String Student_Name = StudentName.getText().toString();
                final String Student_Class = StudentClass.getText().toString();
                final String Student_Subject = StudentSubject.getText().toString();
                final String Student_Contact_No = StudentContact.getText().toString();
                AlertDialog.Builder alert = new AlertDialog.Builder(Enquiry_Details.this);
                alert.setTitle("Thanku We Reach To you Soon");
                alert.setMessage("Please Select Email To To Send Enquiry");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Details = "Name Of Student : "+ Student_Name +" \n  Class - "+Student_Class+"\n Subjects - "+Student_Subject+" \n Contact Number -"+Student_Contact_No;
                        Intent Email_Sent = new Intent(Intent.ACTION_SEND);
                        Email_Sent.setData(Uri.parse("email"));
                        String[] emails = {"abc@gmail.com","kr.rakesh186@gmail.com"};
                        Email_Sent.putExtra(Intent.EXTRA_EMAIL, emails);
                        Email_Sent.putExtra(Intent.EXTRA_SUBJECT,"Enquiry  ");
                        Email_Sent.putExtra(Intent.EXTRA_TEXT,Details);
                        Email_Sent.setType("message/rfc822");
                        Intent intent = Email_Sent.putExtra(Intent.EXTRA_TEXT,Details);
                        Intent chooser = Intent.createChooser(Email_Sent,"Launch Mail");
                        startActivity(chooser);
                        finish();


                    }
                });

            }
        });
    }

    }


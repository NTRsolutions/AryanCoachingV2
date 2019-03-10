package com.example.rakesh.aryancoaching;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RegistrationTutor extends AppCompatActivity {
    private Button paymentButton;

    private EditText nameRt,QalificationRt,subRt,contactRt,emailRt,adreessRt,ptRt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_tutor);
        paymentButton = findViewById(R.id.payment_button);
//


        nameRt = findViewById(R.id.teacher_name);
        QalificationRt = findViewById(R.id.teacher_qualificatio);
        subRt = findViewById(R.id.teacher_subject);
        contactRt = findViewById(R.id.teacher_contact_no);
        emailRt = findViewById(R.id.teacher_email);
        adreessRt = findViewById(R.id.teacher_address);
        ptRt = findViewById(R.id.teacher_timing);



        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder adbRT = new AlertDialog.Builder(RegistrationTutor.this);
                TextView tv = new TextView(RegistrationTutor.this);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(Html.fromHtml("<a href=https://paytm.com>Click here<a/>"));
                adbRT.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nameRt.setText("");
                        QalificationRt.setText("");
                        subRt.setText("");
                        contactRt.setText("");
                        emailRt.setText("");
                        adreessRt.setText("");
                        ptRt.setText("");

                    }
                });
                adbRT.setView(tv);
                adbRT.setNegativeButton("Cancel",null);
                adbRT.show();
//                (TextView)this.get


            }
        });


}

}

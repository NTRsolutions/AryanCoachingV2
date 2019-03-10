package com.example.rakesh.aryancoaching;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rakesh.aryancoaching.Notification.Common;

import com.google.firebase.iid.FirebaseInstanceId;
import com.example.rakesh.aryancoaching.Notification.*;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminField extends AppCompatActivity {
    Button buttonSendData;
    EditText editTitle,editContent;
    APIService mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_field);
        Common.currentToken  = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("MyTopic");




        mService = Common.getFCMClient();
        buttonSendData = (Button)findViewById(R.id.admin_notification_send_button);
        editTitle = (EditText)findViewById(R.id.admin_notification_title);
        editContent = (EditText)findViewById(R.id.admin_notification_content);
        buttonSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Notification notification = new Notification(editTitle.getText().toString(),editContent.getText().toString());
                Sender sender = new Sender("/topics/MyTopic",notification);
                Toast.makeText(AdminField.this,"Notification Sent Succesfully",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder adbM = new AlertDialog.Builder(AdminField.this);
                adbM.setTitle("Want To Send More Notifications");
                adbM.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent  = new Intent(AdminField.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
                adbM.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editTitle.setText("");
                        editTitle.setText("");

                    }
                });


                mService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        if (response.body().success == 1){
                            Toast.makeText(AdminField.this,"Notification Sent Succesfully",Toast.LENGTH_SHORT).show();
                        }



                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        Log.e("Error",t.getMessage());
                        Toast.makeText(AdminField.this,"Notification Sent Failed",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

}

package com.example.rakesh.aryancoaching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);
        final   EditText user_name = (EditText) findViewById(R.id.admin_username);
        final EditText admin_password = (EditText) findViewById(R.id.admin_password);




      final   Button login_button = (Button)findViewById(R.id.admin_login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String username =  user_name.getText().toString();
                //String password = admin_password.getText().toString();
                if (user_name.getText().toString().equals("Aryan")&& admin_password.getText().toString().equals("developer"))
                {
                    Intent admin_field = new Intent(AdminLoginPage.this,AdminField.class);
                    startActivity(admin_field);
                }
                else {
                    Toast.makeText(AdminLoginPage.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
            });
        }

}

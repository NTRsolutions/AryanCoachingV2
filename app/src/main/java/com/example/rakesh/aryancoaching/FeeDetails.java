package com.example.rakesh.aryancoaching;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class FeeDetails extends AppCompatActivity {
    List<String> list;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_details);
        list = new ArrayList<>();
        list.add("class 11-12");
        list.add("class 9-10");
        list.add("class 7-8");
        list.add("class 5-6");
        list.add("class 3-4");
        list.add("class 1-2");
        listView = (ListView) findViewById(R.id.fee_list_view);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FeeDetails.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FeeDetails.this," Selected "+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
//                String selection = parent.getItemAtPosition(position).toString();
                switch (position)
                {
                    case 0:
                        AlertDialog.Builder adb = new AlertDialog.Builder(FeeDetails.this);
                        adb.setTitle("700 per month/Subject");
                        adb.setMessage("For More Than One Subject Fee Is Negotiable");
                        adb.setPositiveButton("ok",null);
                        adb.show();
                        break;
                    case 1:
                        AlertDialog.Builder adb1 = new AlertDialog.Builder(FeeDetails.this);
                        adb1.setTitle("600 per month/Subject");
                        adb1.setMessage(" For More Than One Subject Fee Is Negotiable");
                        adb1.setPositiveButton("ok",null);
                        adb1.show();
                        break;
                    case 2:
                        AlertDialog.Builder adb2 = new AlertDialog.Builder(FeeDetails.this);
                        adb2.setTitle("500 per month/Subject");
                        adb2.setMessage("For More Than One Subject Fee Is Negotiable");
                        adb2.setPositiveButton("ok",null);
                        adb2.show();
                        break;
                    case 3:
                        AlertDialog.Builder adb3 = new AlertDialog.Builder(FeeDetails.this);
                        adb3.setTitle("500 per month/Subject");
                        adb3.setMessage(" For More Than One Subject Fee Is Negotiable");
                        adb3.setPositiveButton("ok",null);
                        adb3.show();
                        break;
                    case 4:
                        AlertDialog.Builder adb4 = new AlertDialog.Builder(FeeDetails.this);
                        adb4.setTitle("500 per month/Subject");
                        adb4.setMessage(" For More Than One Subject Fee Is Negotiable ");
                        adb4.setPositiveButton("ok",null);
                        adb4.show();
                        break;
                    case 5:
                        AlertDialog.Builder adb5 = new AlertDialog.Builder(FeeDetails.this);
                        adb5.setTitle("400 per month/Subject");
                        adb5.setMessage(" For More Than One Subject Fee Is Negotiable");
                        adb5.setPositiveButton("ok",null);
                        adb5.show();
                        break;

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

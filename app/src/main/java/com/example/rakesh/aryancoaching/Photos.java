package com.example.rakesh.aryancoaching;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class Photos extends AppCompatActivity {
    final String photosfolder = "photos";
    final String impfolder = "Important";
    final String bannerfolder = "New One";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);


        CardView photos = findViewById(R.id.photocardview);
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Photos.this, ImagesActivity.class);
                intent.putExtra("FolderName",photosfolder);
                startActivity(intent);
            }
        });
        CardView impphotos = findViewById(R.id.importantcardview);
        impphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Photos.this, ImagesActivity.class);
                intent.putExtra("FolderName",impfolder);
                startActivity(intent);
            }
        });
        CardView banner = findViewById(R.id.banner);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Photos.this, ImagesActivity.class);
                intent.putExtra("FolderName",bannerfolder);
                startActivity(intent);
            }
        });

        final FloatingActionButton floatingActionButton = findViewById(R.id.fabphotos);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Photos.this, Gallery.class);
                startActivity(i);

            }
        });

//
    }
}

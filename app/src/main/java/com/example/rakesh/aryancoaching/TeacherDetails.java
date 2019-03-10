package com.example.rakesh.aryancoaching;

import android.media.tv.TvContentRating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class TeacherDetails extends AppCompatActivity {
    String tname,tu;

    ImageView i1;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);
        i1 = findViewById(R.id.teacherDetailsImage);
        t = findViewById(R.id.teacherDetailsName);


        Bundle extras = getIntent().getExtras();
        tname = extras.getString("Imageurlt");
        tu = extras.getString("Name");
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.coachingicon1).dontAnimate();
        Glide.with(TeacherDetails.this).load(tname).apply(requestOptions).into(i1);
        t.setText(tname);

    }
}

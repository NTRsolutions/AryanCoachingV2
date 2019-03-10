package com.example.rakesh.aryancoaching;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FullImageView extends AppCompatActivity {
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        ImageView fIV = findViewById(R.id.fullimageView);

        Bundle extras = getIntent().getExtras();
        str = extras.getString("Imagei");
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.coachingicon1).dontAnimate();
        Glide.with(this).load(str).apply(requestOptions).into(fIV);
    }
}

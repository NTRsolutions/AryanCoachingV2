package com.example.rakesh.aryancoaching;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.UUID;

public class ImagesActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {
        private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;

    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    private ProgressBar mProgressBarCircle;
    private List<uploads> muploads;
    private StorageReference StrRef;
    private FirebaseStorage fStr;
    private StorageTask st;

     String fname;
     public static final int permissionrequestcode = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBarCircle = findViewById(R.id.progrees_circle);

        muploads = new ArrayList<>();
        mImageAdapter = new ImageAdapter(ImagesActivity.this,muploads);

        mRecyclerView.setAdapter(mImageAdapter);
        mImageAdapter.setOnItemClickListener(ImagesActivity.this);
        // for specific folder
        Bundle extras = getIntent().getExtras();
        fname = extras.getString("FolderName");
//        mStorage = FirebaseStorage.getInstance();
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
//       mDBListener =  mDatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                muploads.clear();
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
//                    uploads upload = postSnapshot.getValue(uploads.class);
//                    upload.setKey(postSnapshot.getKey());
//                    muploads.add(upload);
//                }
//                mImageAdapter.notifyDataSetChanged();
//                mProgressBarCircle.setVisibility(View.INVISIBLE);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(ImagesActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
//                mProgressBarCircle.setVisibility(View.INVISIBLE);
//
//            }
//        });
       if (fname.equalsIgnoreCase("photos"))
       {
           mStorage = FirebaseStorage.getInstance();
           mDatabaseRef = FirebaseDatabase.getInstance().getReference("photos");
           mDBListener =  mDatabaseRef.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   muploads.clear();
                   for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                       uploads upload = postSnapshot.getValue(uploads.class);
                       upload.setKey(postSnapshot.getKey());
                       muploads.add(upload);
                   }
                   mImageAdapter.notifyDataSetChanged();
                   mProgressBarCircle.setVisibility(View.INVISIBLE);

               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {
                   Toast.makeText(ImagesActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                   mProgressBarCircle.setVisibility(View.INVISIBLE);

               }
           });
       }
       else if (fname.equalsIgnoreCase("Important"))
       {
           mStorage = FirebaseStorage.getInstance();
           mDatabaseRef = FirebaseDatabase.getInstance().getReference("Important");
           mDBListener =  mDatabaseRef.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   muploads.clear();
                   for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                       uploads upload = postSnapshot.getValue(uploads.class);
                       upload.setKey(postSnapshot.getKey());
                       muploads.add(upload);
                   }
                   mImageAdapter.notifyDataSetChanged();
                   mProgressBarCircle.setVisibility(View.INVISIBLE);

               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {
                   Toast.makeText(ImagesActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                   mProgressBarCircle.setVisibility(View.INVISIBLE);

               }
           });

       }
       else if (fname.equalsIgnoreCase("New One"))
       {
           mStorage = FirebaseStorage.getInstance();
           mDatabaseRef = FirebaseDatabase.getInstance().getReference("New One");
           mDBListener =  mDatabaseRef.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   muploads.clear();
                   for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                       uploads upload = postSnapshot.getValue(uploads.class);
                       upload.setKey(postSnapshot.getKey());
                       muploads.add(upload);
                   }
                   mImageAdapter.notifyDataSetChanged();
                   mProgressBarCircle.setVisibility(View.INVISIBLE);

               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {
                   Toast.makeText(ImagesActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                   mProgressBarCircle.setVisibility(View.INVISIBLE);

               }
           });
       }
       else
       {
                   mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
       mDBListener =  mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                muploads.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    uploads upload = postSnapshot.getValue(uploads.class);
                    upload.setKey(postSnapshot.getKey());
                    muploads.add(upload);
                }


                mImageAdapter.notifyDataSetChanged();
                mProgressBarCircle.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {
                Toast.makeText(ImagesActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressBarCircle.setVisibility(View.INVISIBLE);

            }
        });
       }
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(ImagesActivity.this,"Click",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewImage(int position) {
        uploads selectedItem = muploads.get(position);
        final String sk = selectedItem.getKey();
        StorageReference ir = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        ir.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri) {
                Intent i = new Intent(ImagesActivity.this,FullImageView.class);
                final String p = uri.toString();
                i.putExtra("Imagei",p);
                startActivity(i);
            }
        });


    }

    @Override
    public void onDownload(int position) {
        uploads ItemSelected = muploads.get(position);
        final String sk = ItemSelected.getKey();
        StorageReference ir1 = mStorage.getReferenceFromUrl(ItemSelected.getImageUrl());
        ir1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri) {
                String link = uri.toString();
                AlertDialog.Builder dialog = new AlertDialog.Builder(ImagesActivity.this);
                dialog.show();
                dialog.setMessage("Downloading.......");
                String filename = UUID.randomUUID().toString()+".jpg";
                Picasso.get().load(link).into(new SaveImageHelper(getApplicationContext(),dialog,getContentResolver() ,filename,"Image Desc"));

//                DownloadFunction(link);
            }
        });


    }

    @Override
    public void onDeleteClick(int position) {
        uploads selectedItem = muploads.get(position);
        final String selectedKey = selectedItem.getKey();
        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(ImagesActivity.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
    public void DownloadFunction(String Msg)
    {

        if (ActivityCompat.checkSelfPermission(ImagesActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED);
        {
            Toast.makeText(ImagesActivity.this,"You Should Grant Permission",Toast.LENGTH_SHORT).show();
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},permissionrequestcode);
//            return;

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case permissionrequestcode:
            {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(ImagesActivity.this,"PERMISSION GRANTED",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ImagesActivity.this,"PERMISSION DENIED",Toast.LENGTH_SHORT).show();
                break;

            }
        }
    }
}

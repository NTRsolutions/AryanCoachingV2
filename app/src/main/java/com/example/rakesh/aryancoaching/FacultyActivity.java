package com.example.rakesh.aryancoaching;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FacultyActivity extends AppCompatActivity implements FacultyCustomAdapter.OnItemClickListener {
    private DatabaseReference mfacultyDatabasereference;
    private RecyclerView recyclerFView;
    private List<Faculty> facultyList;
    private ValueEventListener mfacultyDBListener;
    private ProgressBar mfacultyProgressBarCircle;
    private FacultyCustomAdapter facultyCustomAdapter;
    private FirebaseStorage mfacultyStorage;

    String tt,a1,a2,a3,a4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        Toast.makeText(FacultyActivity.this, "Please Wait While Loading......", Toast.LENGTH_LONG).show();

        FloatingActionButton floatingActionButtonFaculty =  findViewById(R.id.facultyFab);
        floatingActionButtonFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Fill The Enquiry Form", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(FacultyActivity.this, AddFacultyTeacher.class);
                startActivity(intent);
            }
        });


        recyclerFView = findViewById(R.id.facultyReacyclerView);
        recyclerFView.setHasFixedSize(true);
        recyclerFView.setLayoutManager(new LinearLayoutManager(this));
        facultyList = new ArrayList<>();

        mfacultyProgressBarCircle = findViewById(R.id.faculty_progrees_circle);
        facultyCustomAdapter = new FacultyCustomAdapter(FacultyActivity.this, (ArrayList<Faculty>) facultyList);

//        facultyCustomAdapter = new FacultyCustomAdapter(FacultyActivity.this,facultyList);

        recyclerFView.setAdapter(facultyCustomAdapter);
        facultyCustomAdapter.setOnItemClickListener(FacultyActivity.this);
        // for specific folder

        mfacultyStorage = FirebaseStorage.getInstance();
        mfacultyDatabasereference = FirebaseDatabase.getInstance().getReference("Faculty Lists");
        mfacultyDBListener = mfacultyDatabasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                facultyList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Faculty newFaculty = postSnapshot.getValue(Faculty.class);
                    newFaculty.setKey(postSnapshot.getKey());
                    facultyList.add(newFaculty);
                }
                facultyCustomAdapter.notifyDataSetChanged();
                mfacultyProgressBarCircle.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FacultyActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mfacultyProgressBarCircle.setVisibility(View.INVISIBLE);

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(FacultyActivity.this,"Long Press Do Any Action",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteClick(int position) {
        Faculty deleteSecleted = facultyList.get(position);
        final String selectedKey = deleteSecleted.getKey();
        StorageReference imageRef = mfacultyStorage.getReferenceFromUrl(deleteSecleted.getFacultyPic());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mfacultyDatabasereference.child(selectedKey).removeValue();
                Toast.makeText(FacultyActivity.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onViewDetail(int position) {
        Faculty selectedTeacher = facultyList.get(position);
        final String sk = selectedTeacher.getKey();
        StorageReference st = mfacultyStorage.getReferenceFromUrl(selectedTeacher.getFacultyPic());

        StorageReference s  = mfacultyStorage.getReference().child("Faculty Lists");

        st.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri) {

                Intent i = new Intent(FacultyActivity.this,TeacherDetails.class);
                final String urlt = uri.toString();
                i.putExtra("Imageurlt",urlt);
                i.putExtra("Name",a1);
                startActivity(i);
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mfacultyDatabasereference.removeEventListener(mfacultyDBListener);
    }




}


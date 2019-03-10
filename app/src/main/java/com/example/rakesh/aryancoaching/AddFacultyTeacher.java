package com.example.rakesh.aryancoaching;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddFacultyTeacher extends AppCompatActivity {
    private EditText teacherName, teacherSub, teacherQalification, teacherDesc;
    private Button addteacherBtn, imgChooseBtn;
    private ImageView teacherImage;
    private static final int PICK_FACULTY_IMAGE_REQUEST = 1;

    private ProgressBar mfacultyProgressBar;
    private Uri mfacultyImageUri;
    private StorageReference mfacultyStorageRef;
    private DatabaseReference mfacultyDatabaseRef;
    private StorageTask mfacultyUploadTask;
    String facultyImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty_teacher);

        teacherName = findViewById(R.id.newteacherName);
        teacherSub = findViewById(R.id.newteacherSubject);
        teacherQalification = findViewById(R.id.newteacherQualification);
        teacherDesc = findViewById(R.id.newteacherDescription);
        addteacherBtn = findViewById(R.id.addnewteacherBtn);
        teacherImage = findViewById(R.id.newteacherImg);
        imgChooseBtn = findViewById(R.id.newteacherImgChooseBtn);
        mfacultyProgressBar = (ProgressBar)findViewById(R.id.facultyProgressBar);

        imgChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_FACULTY_IMAGE_REQUEST);
            }
        });
        mfacultyStorageRef = FirebaseStorage.getInstance().getReference("Faculty Lists");
        mfacultyDatabaseRef = FirebaseDatabase.getInstance().getReference("Faculty Lists");
        addteacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mfacultyUploadTask != null && mfacultyUploadTask.isInProgress()) {
                    Toast.makeText(AddFacultyTeacher.this, "Upload Is In progress", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    facultyuploadfile();
                   // newfunct();

                    mfacultyProgressBar.setVisibility(View.VISIBLE);
                }

            }
        });


    }

    public void facultyuploadfile() {
        if (mfacultyImageUri != null) {
            final StorageReference fileReference = mfacultyStorageRef.child(System.currentTimeMillis() + "." + getfacultyFileExtension(mfacultyImageUri));
//
            final String filecontext = fileReference.toString();
            final StorageReference refFaculty = mfacultyStorageRef.child("Faculty List");


            mfacultyUploadTask = refFaculty.putFile(mfacultyImageUri);
//
            Task<Uri> urlFacultyTask = mfacultyUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return refFaculty.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        facultyImageUrl = downloadUri.toString();

                        Faculty faculty = new Faculty(facultyImageUrl, teacherName.getText().toString(), teacherSub.getText().toString(), teacherQalification.getText().toString(), teacherDesc.getText().toString());
                        String uploadfacultyId = mfacultyDatabaseRef.push().getKey();
                        mfacultyDatabaseRef.child(uploadfacultyId).setValue(faculty);
                        mfacultyProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(AddFacultyTeacher.this, "Successfully Added", Toast.LENGTH_SHORT).show();


                        // For Dialog Box
                        AlertDialog.Builder adb = new AlertDialog.Builder(AddFacultyTeacher.this);
                        adb.setMessage("Want To Add More Teachers!!!!");
                        adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                teacherImage.setImageResource(android.R.color.transparent);
                                teacherName.setText(null);
                                teacherSub.setText(null);
                                teacherQalification.setText(null);
                                teacherDesc.setText(null);

                            }
                        });
                        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(AddFacultyTeacher.this, FacultyActivity.class);
                                startActivity(i);

                            }
                        });
                        adb.show();
                    } else {

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddFacultyTeacher.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(AddFacultyTeacher.this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }
//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FACULTY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mfacultyImageUri = data.getData();
//            Picasso.get().load(mfacultyImageUri).into(teacherImage);
            Glide.with(AddFacultyTeacher.this).load(mfacultyImageUri).into(teacherImage);
        }
    }

    private String getfacultyFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
    private void newfunct() {
        if (mfacultyImageUri != null) {
            final StorageReference facultyfileReference = mfacultyStorageRef.child(System.currentTimeMillis() + "." + getfacultyFileExtension(mfacultyImageUri));
//
            mfacultyUploadTask = facultyfileReference.putFile(mfacultyImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
                    Toast.makeText(AddFacultyTeacher.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    mfacultyProgressBar.setVisibility(View.INVISIBLE);
                    Faculty ff = new Faculty(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(), teacherName.getText().toString(), teacherSub.getText().toString(), teacherQalification.getText().toString(), teacherDesc.getText().toString());
                    String fId = mfacultyDatabaseRef.push().getKey();
                    mfacultyDatabaseRef.child(fId).setValue(ff);
//

                    //For DialogBox

                    AlertDialog.Builder adb = new AlertDialog.Builder(AddFacultyTeacher.this);
                    adb.setMessage("Want To Add More Teachers!!!!");
                    adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            teacherImage.setImageResource(android.R.color.transparent);
                            teacherName.setText(null);
                            teacherSub.setText(null);
                            teacherQalification.setText(null);
                            teacherDesc.setText(null);

                        }
                    });
                    adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(AddFacultyTeacher.this, FacultyActivity.class);
                            startActivity(i);

                        }
                    });
                    adb.show();


                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddFacultyTeacher.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
//      }


        }
//
    }}


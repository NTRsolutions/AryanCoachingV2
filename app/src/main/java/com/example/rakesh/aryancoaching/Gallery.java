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
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class Gallery extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonchooseImage;
    private TextView mTextViewShowUploads;
    private EditText mEditTextfileName;
    private Button mUploadButton;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    String miUrlOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        mButtonchooseImage = findViewById(R.id.choose_button);
        mTextViewShowUploads = findViewById(R.id.show_uploads);
        mEditTextfileName = findViewById(R.id.file_name);
        mImageView = findViewById(R.id.uploload_image);
        mProgressBar = findViewById(R.id.progreesbar);
        mUploadButton = findViewById(R.id.upload_button);


        // for choosing specific folder

        final String photosfolder = "photos";
        final String impfolder = "Important";
        final String bannerfolder = "New One";


//        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        mButtonchooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooser();

            }
        });
        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, ImagesActivity.class);
                startActivity(intent);

            }
        });
        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Gallery.this, "Upload Is In progress", Toast.LENGTH_SHORT).show();
                } else {
//                     uploadfile();
                    PopupMenu popupMenu = new PopupMenu(Gallery.this, mUploadButton);
                    popupMenu.getMenuInflater().inflate(R.menu.photos_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int id = menuItem.getItemId();
                            Toast.makeText(Gallery.this, "Select Folder Name", Toast.LENGTH_SHORT).show();
                            switch (id) {
                                case R.id.photos:

                                    mStorageRef = FirebaseStorage.getInstance().getReference("photos");
                                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("photos");
                                    uploadfile("photos");
                                    break;


                                case R.id.impphotos:

                                    mStorageRef = FirebaseStorage.getInstance().getReference("Important");
                                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("Important");
                                    uploadfile("Important");
                                    break;

                                case R.id.banner:

                                    mStorageRef = FirebaseStorage.getInstance().getReference("New One");
                                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("New One");
                                    uploadfile("New One");
                                    break;
                                default:
                                    mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
                                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
                                    uploadfile("uploads");
                                    break;


                            }

                            return true;
                        }
                    });
                    popupMenu.show();


                }
            }
        });
    }

    private void openfilechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadfile(String filename) {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
//
            final String filecontext = fileReference.toString();
            final StorageReference ref = mStorageRef.child(filename);
            mUploadTask = ref.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  clearProgressBar();
                }
            });

            mUploadTask = ref.putFile(mImageUri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);

                }
            });
            Task<android.net.Uri> urlTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String miUrlOk = downloadUri.toString();

                        uploads Uploads = new uploads(mEditTextfileName.getText().toString().trim(), miUrlOk);
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(Uploads);
                        Toast.makeText(Gallery.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();

                        // For Dialog Box
                        AlertDialog.Builder adb = new AlertDialog.Builder(Gallery.this);
                        adb.setMessage("Want To Add More Photos!!!!");
                        adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mImageView.setImageResource(android.R.color.transparent);
                                mEditTextfileName.setText(null);
                            }
                        });
                        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Gallery.this, Photos.class);
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
                    Toast.makeText(Gallery.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(Gallery.this, "No File Selected", Toast.LENGTH_SHORT).show();
        }

    }
    public void clearProgressBar()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setProgress(0);
            }
        }, 500);
        Toast.makeText(Gallery.this, "Uploaded Succesfully ", Toast.LENGTH_SHORT).show();
    }
}

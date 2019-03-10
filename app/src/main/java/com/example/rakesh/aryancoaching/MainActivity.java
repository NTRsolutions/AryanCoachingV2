package com.example.rakesh.aryancoaching;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.GridLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;
    private static final int RC_PHOTO_PICKER = 2;
    //FireBase Instances
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;

    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
//                    Toast.makeText(MainActivity.this, "Welcome To Aryan Coaching Institute", Toast.LENGTH_SHORT).show();
//                    onSignedInInitialize(user.getDisplayName());
                } else {
                    //user is signed out
                    onSignedOutCleanup();
                    List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(), new AuthUI.IdpConfig.PhoneBuilder().build(), new AuthUI.IdpConfig.GoogleBuilder().build());
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setAvailableProviders(providers).build(), RC_SIGN_IN);
                }
            }
        };
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // For Enquiry
        CardView enqiry_view = (CardView) findViewById(R.id.Enquiry_Button);
        enqiry_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Enquiry_Details.class);
                startActivity(intent);

            }
        });


        CardView location_view = (CardView) findViewById(R.id.location_button);
        location_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Going To maps", Toast.LENGTH_SHORT).show();
                Intent Location_Intent = new Intent(Intent.ACTION_VIEW);
                Location_Intent.setData(Uri.parse("geo:0,0?q= ARYAN COACHING INSTITUTE 203 Jharoda Burari"));
                Intent chooser = Intent.createChooser(Location_Intent, "Launch Maps");
                startActivity(chooser);

            }
        });
        CardView Faculty = (CardView) findViewById(R.id.Faculty_card);
        Faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, FacultyActivity.class);
                startActivity(intent);


            }
        });
        CardView AboutUs = (CardView) findViewById(R.id.About_us);
        AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(intent);

            }
        });
        CardView Fee_details = (CardView) findViewById(R.id.fee_structure);
        Fee_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fee_activity = new Intent(MainActivity.this, FeeDetails.class);
                startActivity(fee_activity);
            }
        });
        CardView Batches = (CardView) findViewById(R.id.batch_card);
        Batches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fee_activity = new Intent(MainActivity.this, BatchActivity.class);
                startActivity(fee_activity);

            }
        });

        CardView payfee = (CardView) findViewById(R.id.payfee);
        payfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder feeAlert = new AlertDialog.Builder(MainActivity.this);
                TextView tv = new TextView(MainActivity.this);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                feeAlert.setTitle("Pay your Fee With Paytm ");
                tv.setText(Html.fromHtml("<a href=https://paytm.com>Click here to Pay Your Fee <a/>"));
                feeAlert.setView(tv);
                feeAlert.setNegativeButton("Cancel", null);
                feeAlert.setPositiveButton("ok", null);
                feeAlert.show();
            }
        });


        final CardView Home_Tution = (CardView) findViewById(R.id.Home_tution_button);
        Home_Tution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent hometution_intent = new Intent(MainActivity.this, HomeTutionActivity.class);
                startActivity(hometution_intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Fill The Enquiry Form", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, Enquiry_Details.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.SignOut) {
            AuthUI.getInstance().signOut(this);
            return true;
        }
        if (id == R.id.admin_login) {
            Intent admin_login_page = new Intent(MainActivity.this, AdminLoginPage.class);
            startActivity(admin_login_page);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Contact_Us) {
            Intent Calling_intent = new Intent(Intent.ACTION_DIAL);
            Calling_intent.setData(Uri.parse("tel:7840890533"));
            startActivity(Calling_intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(MainActivity.this, Photos.class);
            startActivity(intent);


        } else if (id == R.id.nav_slideshow) {


        } else if (id == R.id.nav_manage) {


        } else if (id == R.id.nav_share) {
            Intent SharingIntent = new Intent(Intent.ACTION_SEND);
            SharingIntent.setType("text/plain");
            SharingIntent.putExtra(Intent.EXTRA_TEXT, "This Is The Best Place TO Study With Fun" + "\n" + "http.//www.aryanCoachinginstitute.com");
            startActivity(SharingIntent);


        } else if (id == R.id.nav_send) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseListener();
//        mMessageAdapter.clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed in !", Toast.LENGTH_SHORT).show();
            } else if (requestCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in cancelled !", Toast.LENGTH_SHORT).show();
                finish();
            } else if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                StorageReference photoRef = mChatPhotosStorageReference.child(selectedImageUri.getLastPathSegment());
                photoRef.putFile(selectedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        mMessageDatabaseReference.push().setValue(friendlyMessage);
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void onSignedInInitialize(String username) {
        mUsername = username;
//        attachDatabaseReadListener();

    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
//        mMessageAdapter.clear();
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
//                    mMessageAdapter.add(friendlyMessage);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
//            mMessageDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseListener() {
        if (mChildEventListener != null) {
            mChildEventListener = null;
        }
    }
}
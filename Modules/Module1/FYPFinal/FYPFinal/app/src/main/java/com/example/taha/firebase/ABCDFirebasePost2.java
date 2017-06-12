package com.example.taha.firebase;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.taha.fypfinal.CustomizeButton2;
import com.example.taha.fypfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ABCDFirebasePost2 extends AppCompatActivity {
    Button button3;
    CameraPhoto cameraPhoto;
    final int CAMERA_REQUEST=13323;
    EditText textview_Address,Title,Desc;

    StorageReference storageReference;
    static final int CAMERA_REQUEST_CODE=1;

ImageView id1,id2;
String address="";
    String ImageAddress="";

    Bitmap bitmap;
    ProgressDialog progress;
    ///////////////////////////////////////fIREBASE/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
    StorageReference mStorage;
   DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    DatabaseReference mDatabaseUser;
    DatabaseReference mDatabaseLike;
    DatabaseReference mDatabasedisLike;
    ///////////////////////////////////////fIREBASE/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
    @TargetApi(Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abcdfirebase_post2);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+05:00"));
        String strDate="";
        ///////////////////////////////////////fIREBASE/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
        mStorage= FirebaseStorage.getInstance().getReference();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        mAuth= FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();

        storageReference= FirebaseStorage.getInstance().getReference();

        ///////////////////////////////////////fIREBASE/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
        progress=new ProgressDialog(this);
        Bundle extras = getIntent().getExtras();
        address= (String) extras.get("address");

        ////////
        id2= (ImageView) findViewById(R.id.imageView);
        id1= (ImageView) findViewById(R.id.imageView4);

        Title= (EditText) findViewById(R.id.editText4);
        Desc= (EditText) findViewById(R.id.editText7);
        textview_Address= (EditText) findViewById(R.id.editText8);
        button3= (Button) findViewById(R.id.button3);
       //////
        cameraPhoto=new CameraPhoto((getApplicationContext()));

        String mdformat = null;
     //   if (android.os.Build.VERSION.SDK_INT >=23) {
      //     mdformat =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(Calendar.getInstance().getTime());
     //  }
      // if (android.os.Build.VERSION.SDK_INT >=23) {
            strDate =  mdformat.format(String.valueOf(calendar.getTime()));
    //  }
        address=strDate+","+address;


        textview_Address.setText(address);

        //////////Image Work///////////////////////
        id1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("my","OnClick is called");
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST_CODE);
            }
        });
        //////////////////////////////////////////

        button3.setOnClickListener(new View.OnClickListener() {
                                       @Override
                  public void onClick(View view)
                                       {

                                           progress.setMessage("Posting...");
                                           progress.show();
                                         startPosting();

//                                           Intent myIntent = new Intent(view.getContext(),
//                                                   AABCDFirebase1.class);
//
//
//
//                                           startActivity(myIntent);


                                       }
                                   });

//
    }//main

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {  super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST_CODE && resultCode==RESULT_OK){

            Uri uri=data.getData();
            StorageReference filepath=storageReference.child("Photos").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ABCDFirebasePost2.this, "Uploading finish...", Toast.LENGTH_SHORT).show();
                    Uri downloadUri=taskSnapshot.getDownloadUrl();
                    ImageAddress= String.valueOf(downloadUri);
                    Log.d("Path", ImageAddress);
                    Picasso.with(ABCDFirebasePost2.this).load(downloadUri).fit().centerCrop().into(id2);


                }
            });
        }
    }




    ///////////////////////////////////////fIREBASE/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
    void startPosting()
    {

        final String title= String.valueOf(Title.getText()).trim();
        final String desc=String.valueOf(Desc.getText()).trim();

//              StorageReference filepath=mStorage.child("Post_Image").child(id2);

     final   DatabaseReference newPost=mDatabase.push();

        /////////////////////Extract UserName///////////////////////
        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                newPost.child("title").setValue(title);
                newPost.child("desc").setValue(desc);
                newPost.child("location").setValue(address);
                newPost.child("uid").setValue(mCurrentUser.getUid());
                newPost.child("image").setValue(ImageAddress);
                newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ABCDFirebasePost2.this, "Post Submitted", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(ABCDFirebasePost2.this,
                                                 AABCDFirebase1.class);
                                   startActivity(myIntent);
                        }

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        /////////////////////Extract UserName///////////////////////


            progress.dismiss();


    }
    ///////////////////////////////////////fIREBASE/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////


}

package com.example.taha.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.taha.fypfinal.CustomizeButton2;
import com.example.taha.fypfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    EditText nameField;
    EditText EmailField;
    EditText PasswordField;
    Button RegListener,back;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProgressDialog Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");


        Progress=new ProgressDialog(this);

        nameField=(EditText) findViewById(R.id.nameField);
        EmailField=(EditText) findViewById(R.id.emailField);
        PasswordField=(EditText) findViewById(R.id.passwordField);
        RegListener=(Button) findViewById(R.id.registerbutton);
        back=(Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),
                        Login.class);
                           startActivity(myIntent);
            }
        });


      RegListener.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startRegister();

          }
      });



    }


    void startRegister()
    {

   final String name=nameField.getText().toString().trim();
     final   String email=EmailField.getText().toString().trim();
        String password=PasswordField.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) )
        {
            Progress.setMessage("Signing Up...");
            Progress.show();
              mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignIn.this,new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {

                     if(task.isSuccessful())
                    {
                      String user_id=mAuth.getCurrentUser().getUid();

                         DatabaseReference current_user_Db=  mDatabase.child(user_id);

                     current_user_Db.child("name").setValue(name);
                        current_user_Db.child("email").setValue(email);
                     current_user_Db.child("image").setValue("default");
                     Progress.dismiss();

                         Intent homeIntent=new Intent(SignIn.this,AABCDFirebase1.class);
                         homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(homeIntent);
                     }

                  }
              });
        }






    }

}

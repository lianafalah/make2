package com.example.slider;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirstActivity extends AppCompatActivity {
    private EditText email,pass;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        email=(EditText)findViewById(R.id.Email);
        pass=(EditText)findViewById(R.id.Password);
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference().child("users");


    }
    public void signupButtonClick(View view){

        final String email_text = email.getText().toString().trim();
        String pass_text = pass.getText().toString().trim();

        if(!TextUtils.isEmpty(email_text) && !TextUtils.isEmpty(pass_text)){
            mAuth.createUserWithEmailAndPassword(email_text,pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                public void onComplete(@NonNull Task<AuthResult> task) {
                    //Log.i(TAG, "onComplete: " + task.getResult().getUser() + " exp: " + task.getException());

                    if(task.isSuccessful()){

                        String user_id = mAuth.getCurrentUser().getUid();
//                        Log.d(TAG, "regis: " + user_id);
                        DatabaseReference current_user= mDatabase.child(user_id);

//                        Log.d(TAG, "CUR USER " + current_user);
                        current_user.child("Name").setValue(email_text);

                        Intent login = new Intent(FirstActivity.this,LoginActivity.class);
                        startActivity(login);
                    }
                }
            });
        }
    }

    public void signinButtonClick(View view){
        Intent loginintent= new Intent(FirstActivity.this,LoginActivity.class);
        startActivity(loginintent);
    }

}

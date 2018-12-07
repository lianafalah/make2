package com.example.slider;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText userEmail, userPass;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userEmail = (EditText)findViewById(R.id.Email);
        userPass = (EditText)findViewById(R.id.Password);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    }
    public void signinButtonClicked(View view){
        String email = userEmail.getText().toString().trim();
        String pass = userPass.getText().toString().trim();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   // Log.i(TAG, "onComplete: " + task.getResult().getUser() + " exc: " + task.getException());
                    if(task.isSuccessful()){
                        checkUserExists();
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Uncorrect Email and Password" , Toast.LENGTH_SHORT).show();
        }
    }
    public void checkUserExists(){
        FirebaseUser user = mAuth.getCurrentUser();
        final String user_id = user.getUid();
        Toast.makeText(this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "checkUserExists: id " + user_id);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d(TAG, "onDataChange: " + dataSnapshot.getChildrenCount());
                if(dataSnapshot.hasChild(user_id)){
                    Intent menuIntent= new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(menuIntent);
                }
                else {
                    //Toast.makeText(this,"vailed",Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

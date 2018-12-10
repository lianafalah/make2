package com.example.slider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirstActivity extends AppCompatActivity {
    private EditText email,pass;
   // private Button signOut;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private SignInButton mGoogleBtn;
    private static final int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    private static final String TAG="FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        email=(EditText)findViewById(R.id.Email);
        pass=(EditText)findViewById(R.id.Password);
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference().child("users");

      //  signOut = findViewById(R.id.sign_out);

        //sign google
        mGoogleBtn= (SignInButton) findViewById(R.id.googleBtn);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

       /* signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });*/
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Intent loginintent= new Intent(FirstActivity.this,LoginActivity.class);
                            startActivity(loginintent);
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(FirstActivity.this, "you are not able to login to google ", Toast.LENGTH_LONG).show();
                          //  Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    private void updateUI(FirebaseUser user){

       // signOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null){
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Toast.makeText(this,"Name of the user :" + personName+ " user id is:"+personId, Toast.LENGTH_SHORT).show();
        }
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

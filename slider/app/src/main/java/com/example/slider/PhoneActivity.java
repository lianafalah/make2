package com.example.slider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {


    private Spinner spinner;
    private EditText editText;
    private static final String TAG = "## Test";

    private EditText editTextMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        editTextMobile = findViewById(R.id.editTextMobile);

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

       findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String code = CountryData.countryCode[spinner.getSelectedItemPosition()];
               String number = editTextMobile.getText().toString().trim();
               if(number.isEmpty() || number.length() <10 ){
                   editText.setError("Number is required");
                   editText.requestFocus();
                   return;
               }

               String phoneNumber = "+" + code + number;
               Log.d(TAG, "number: " +phoneNumber);
               Intent intent = new Intent(PhoneActivity.this, VerifyPhoneActivity.class);
               intent.putExtra("phonenumber", phoneNumber);
               startActivity(intent);
           }
       });

    }

}

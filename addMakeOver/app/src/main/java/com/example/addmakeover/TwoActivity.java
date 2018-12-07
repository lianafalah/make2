package com.example.addmakeover;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class TwoActivity extends BaseActivity {
    ArrayList<String> selection= new ArrayList<String>();
    AlertDialog.Builder builder;
    private static final String TAG = "## LIAANA";
    private ImageButton foodImage;
    private static final int GET_FROM_GALLERY=3;
    private EditText name,desc,price;
    private Uri uriImage = null;
    private StorageReference storageReference= null;
    private DatabaseReference mRef;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spinner spinner= findViewById(R.id.listProduct);


        name = (EditText) findViewById(R.id.itemName);
        desc = (EditText) findViewById(R.id.itemDesc);
        price = (EditText) findViewById(R.id.itemPrice);

        storageReference = FirebaseStorage.getInstance().getReference("item");
        Log.i(TAG, "onCreate: " + storageReference.getPath() + " " + storageReference.getName());
        mRef=FirebaseDatabase.getInstance().getReference("item");
        Log.i(TAG, "onCreate: " + mRef.toString());

    }

    @Override
    int getContentViewId() {

        return R.layout.activity_two;
    }

    @Override
    int getNavigationMenuItemId() {

        return R.id.navigation_dashboard;
    }

    public void imageButtonClicked(View view){
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY && resultCode== Activity.RESULT_OK){
            Uri selectedImage = data.getData();
            foodImage =(ImageButton) findViewById(R.id.foodImageButton);
            foodImage.setImageURI(selectedImage);
            uriImage = selectedImage;
        }
    }
    public void addItemButtonClicked(View view) {
        //untuk spinner
        // final String name_text = item.getSelectedItem().toString().trim();


        final String name_text = name.getText().toString().trim();
        final String desc_text = desc.getText().toString().trim();
        final String price_text = price.getText().toString().trim();

        if (!TextUtils.isEmpty(name_text) && !TextUtils.isEmpty(desc_text) && !TextUtils.isEmpty(price_text) && uriImage != null) {

            Log.i(TAG, "addItemButtonClicked: " + uriImage.getPath() + " last " + uriImage.getLastPathSegment());

            final StorageReference filepath = storageReference.child(uriImage.getLastPathSegment());
            filepath.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final DatabaseReference newPost = mRef.push();
                    //untuk check box
                    String final_result= "";
                    for (String Selections : selection){
                        final_result = final_result + Selections;
                    }
                    //newPost.child("name").setValue(strOption);-> untuk radio button
                    //newPost.child("name").setValue(final_result);-> untuk check box
                    newPost.child("name").setValue(name_text);
                    newPost.child("desc").setValue(desc_text);
                    newPost.child("price").setValue(price_text);

                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            newPost.child("image").setValue(uri.toString());
                            Toast.makeText(TwoActivity.this, "Input Sucessed " , Toast.LENGTH_LONG).show();
                            Intent Main= new Intent(TwoActivity.this,MainActivity.class);
                            startActivity(Main);


                        }
                    });
                }
            });
        }
    }

    //membuat dialog
    public void tampilDialog() {

        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Selamat !!!");
        builder.setMessage("Data Berhasil Disimpan");
        builder.setPositiveButton("OKE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(TwoActivity.this, "Selamat", Toast.LENGTH_SHORT).show();
            }
        });


        builder.setNegativeButton("Tambah Lagi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();

    }
}

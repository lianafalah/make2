package com.example.addmakeover;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class SingleProductActivity extends AppCompatActivity {
    private String product_key= null;
    private TextView key1;
    private EditText singleProductTitle,singleProductDesc,singleProductPrice;
    private ImageView singleProductImage;
    private Button orderButton;
    private static final String TAG = "## Test";
    private FirebaseAuth mAuth;
    private Button button2;
    private FirebaseUser current_user;
    private DatabaseReference mRef;
    private DatabaseReference mDatabase,userData,ref;
    private String product_name,product_price,product_desc,product_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        product_key = getIntent().getExtras().getString("ProductId");
        mRef= FirebaseDatabase.getInstance().getReference().child("item").child(product_key);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("item");
        singleProductDesc = (EditText) findViewById(R.id.productDesc);
        singleProductTitle = (EditText) findViewById(R.id.productName);
        singleProductPrice = (EditText) findViewById(R.id.productPrice);
        singleProductImage = (ImageView) findViewById(R.id.productImage);
        singleProductTitle.setText(getIntent().getStringExtra("name"));
        singleProductPrice.setText(getIntent().getStringExtra("price"));
        singleProductDesc.setText(getIntent().getStringExtra("desc"));
        //String key = getIntent().getExtras().get("key").toString();
        //ref = FirebaseDatabase.getInstance().getReference().child("item").child(key);
        key1 = (TextView) findViewById(R.id.key);
        key1.setText(product_key);

        mRef= FirebaseDatabase.getInstance().getReference().child("item").child(product_key);
        mDatabase.child(product_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                product_name=(String)dataSnapshot.child("name").getValue();
                product_price=(String)dataSnapshot.child("price").getValue();
                product_desc=(String)dataSnapshot.child("desc").getValue();
                product_image=(String)dataSnapshot.child("image").getValue();
                singleProductTitle.setText(product_name);
                singleProductPrice.setText(product_price);
                singleProductDesc.setText(product_desc);

                Picasso.with(SingleProductActivity.this).load(product_image).into(singleProductImage);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button2 = (Button) findViewById(R.id.DeleteButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        SingleProductActivity.this);
                builder.setTitle("Warning");
                builder.setMessage("Are you sure to delete? ");
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Log.e("info", "NO");
                            }
                        });
                builder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Log.e("info", "YES");
                                mRef.removeValue();
                                Intent singlefoodActivity = new Intent(SingleProductActivity.this,ViewProductActivity.class);
                                startActivity(singlefoodActivity);
                                Toast.makeText(SingleProductActivity.this,"Succes", Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
            }
        });
    }
    public void UpdateButtonClick(View view){

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, "setName: " + singleProductTitle.getText().toString());
                Log.d(TAG, "setName2: " + singleProductTitle);
                dataSnapshot.getRef().child("name").setValue(singleProductTitle.getText().toString());
                dataSnapshot.getRef().child("desc").setValue(singleProductDesc.getText().toString());
                dataSnapshot.getRef().child("price").setValue(singleProductPrice.getText().toString());
                Toast.makeText(SingleProductActivity.this,"Succesed Update", Toast.LENGTH_LONG).show();
                SingleProductActivity.this.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

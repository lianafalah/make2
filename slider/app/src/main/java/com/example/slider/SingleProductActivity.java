

package com.example.slider;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class SingleProductActivity extends AppCompatActivity {
    private String product_key= null;
    private TextView singleProductTitle,singleProductDesc,singleProductPrice;
    private ImageView singleProductImage;
    private Button orderButton;
    private FirebaseAuth mAuth;
    private Button button2;
    private FirebaseUser current_user;
    private DatabaseReference mRef;
    private DatabaseReference mDatabase,userData;
    private String product_name,product_price,product_desc,product_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        product_key = getIntent().getExtras().getString("ProductId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("item");
        singleProductDesc = (TextView) findViewById(R.id.singleDesc);
        singleProductTitle = (TextView) findViewById(R.id.singleTitle);
        singleProductPrice = (TextView) findViewById(R.id.singlePrice);
        singleProductImage = (ImageView) findViewById(R.id.singleImageView);
        mAuth = FirebaseAuth.getInstance();

        current_user = mAuth.getCurrentUser();
        userData = FirebaseDatabase.getInstance().getReference().child("users").child(current_user.getUid());

        mRef= FirebaseDatabase.getInstance().getReference().child("Orders");
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
        button2 = (Button) findViewById(R.id.orderButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        SingleProductActivity.this);
                builder.setTitle("Contoh Alert");
                builder.setMessage("Alert dengan 2 Action Button ");
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
                            }
                        });
                builder.show();
            }
        });
    }
    //dialog button

    public void orderItemClicked(View view){
        final DatabaseReference newOrder = mRef.push();
        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newOrder.child("itemname").setValue(product_name);
                newOrder.child("username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(SingleProductActivity.this,MenuActivity.class));
                        Toast.makeText(SingleProductActivity.this, "Thank for order", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}

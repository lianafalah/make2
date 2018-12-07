package com.example.addmakeover;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ListProductActivity extends AppCompatActivity {

    private RecyclerView mProductList ;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        mProductList = (RecyclerView)findViewById(R.id.productList);
        mProductList.setHasFixedSize(true);
        mProductList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("item");

        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Intent loginInten = new Intent(ListProductActivity.this,MainActivity.class);
                    loginInten.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginInten);
                }
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<Product,FoodViewHolder> FBRA = new FirebaseRecyclerAdapter<Product, FoodViewHolder>(
                Product.class,
                R.layout.singlelistproduct,
                FoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Product model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                final String food_key = getRef(position).getKey().toString();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singlefoodActivity = new Intent(ListProductActivity.this,SingleProductActivity.class);
                        singlefoodActivity.putExtra("FoodId", food_key);
                        startActivity(singlefoodActivity);
                    }
                });

            }
        };
        mProductList.setAdapter(FBRA);
    }

    public static class FoodViewHolder extends  RecyclerView.ViewHolder{
        private static final String TAG = "## LIANA";
        View mView;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;

        }
        public void setName(String name){
            TextView food_name = (TextView) mView.findViewById(R.id.productName);
            food_name.setText(name);
        }
        public void setPrice(String price){
            TextView food_price = (TextView)mView.findViewById(R.id.productPrice);
            food_price.setText(price);
        }
        public void setDesc(String desc){
            TextView food_desc = (TextView)mView.findViewById(R.id.productDesc);
            food_desc.setText(desc);
        }

        public void setImage(Context ctx, String image){
            ImageView food_image=(ImageView)mView.findViewById(R.id.productImage);
            Log.d(TAG, "setImage: " + image);
            Picasso.with(ctx).load(image).into(food_image);
        }
    }
}

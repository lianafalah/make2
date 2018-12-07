package com.example.slider;

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
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class MenuActivity extends AppCompatActivity {
    private RecyclerView mProductList ;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String item ;
    private static final String TAG = "## Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle b = getIntent().getExtras();
        item = b.getString("parse_nama");

        mProductList = (RecyclerView)findViewById(R.id.productList);
        mProductList.setHasFixedSize(true);
        mProductList.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("item");

        mAuth=FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Intent loginInten = new Intent(MenuActivity.this,MainActivity.class);
                    loginInten.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginInten);
                }
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        Query Queries = mDatabase.orderByChild("item").equalTo("Lips");
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseRecyclerAdapter <Product,ProductViewHolder> FBRA = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.singlemenuitem,
                ProductViewHolder.class,
                Queries
        ) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                final String product_key = getRef(position).getKey().toString();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singlefoodActivity = new Intent(MenuActivity.this,SingleProductActivity.class);
                        singlefoodActivity.putExtra("ProductId", product_key);
                        startActivity(singlefoodActivity);
                    }
                });
            }
        };
        mProductList.setAdapter(FBRA);
    }

    public static class ProductViewHolder extends  RecyclerView.ViewHolder{
        private static final String TAG = "## Test";
        View mView;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setName(String name){
            TextView product_name = (TextView) mView.findViewById(R.id.productName);
            Log.d(TAG, "setName: " + name);
            product_name.setText(name);
        }
        public void setPrice(String price){
            TextView product_price = (TextView)mView.findViewById(R.id.productPrice);
            Log.d(TAG, "setPrice: " + price);
            product_price.setText(price);
        }
        public void setDesc(String desc){
            TextView product_desc = (TextView)mView.findViewById(R.id.productDesc);
            product_desc.setText(desc);
        }
        public void setImage(Context ctx, String image){
            ImageView product_image=(ImageView)mView.findViewById(R.id.productImage);
            Log.d(TAG, "setImage: " + image);
            Picasso.with(ctx).load(image).into(product_image);
        }
    }
}

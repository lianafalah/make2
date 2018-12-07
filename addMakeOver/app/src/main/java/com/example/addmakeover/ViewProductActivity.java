package com.example.addmakeover;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.SearchView;

import android.widget.SearchView.OnQueryTextListener;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewProductActivity extends AppCompatActivity {

    android.support.v7.widget.SearchView searchView;
    private List<String> names;
    private RecyclerView mProductList;
    private DatabaseReference mDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        mProductList = (RecyclerView) findViewById(R.id.viewProduct);
        mProductList.setHasFixedSize(true);
        mProductList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("item");
        mRef= FirebaseDatabase.getInstance().getReference().child("item");
        Query Queries = mRef.orderByChild("name").equalTo("Lipmate");


    }

    //searchdata
    private void firebaseSearch(String searchText){

        Query firebaseSearchQuery = mRef.orderByChild("name").startAt(searchText).endAt(searchText + "\uf0ff");


        FirebaseRecyclerAdapter<Product, ProductViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                        Product.class,
                        R.layout.singlelistproduct,
                        ProductViewHolder.class,
                        firebaseSearchQuery

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
                                Intent singlefoodActivity = new Intent(ViewProductActivity.this,SingleProductActivity.class);
                                singlefoodActivity.putExtra("ProductId", product_key);
                                startActivity(singlefoodActivity);
                            }
                        });
                    }
                };
        mProductList.setAdapter(firebaseRecyclerAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView= (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //muncul yang dicari
                firebaseSearch(newText);

                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        //handle other action

        if( id == R.id.action_setting){
            //TODO
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Query Queries = mRef.orderByChild("name").equalTo("Lipmate");
        FirebaseRecyclerAdapter<Product,ProductViewHolder> FBRA = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.singlelistproduct,
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
                        Intent singlefoodActivity = new Intent(ViewProductActivity.this,SingleProductActivity.class);
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
            TextView product_price = (TextView) mView.findViewById(R.id.productPrice);
            Log.d(TAG, "setPrice: " + price);
            product_price.setText(price);
        }
        public void setDesc(String desc){
            TextView product_desc = (TextView) mView.findViewById(R.id.productDesc);
            product_desc.setText(desc);
        }
        public void setImage(Context ctx, String image){
            ImageView product_image=(ImageView)mView.findViewById(R.id.productImage);
            Log.d(TAG, "setImage: " + image);
            Picasso.with(ctx).load(image).into(product_image);
        }
    }

}

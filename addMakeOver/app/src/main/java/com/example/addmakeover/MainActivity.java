package com.example.addmakeover;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void AddProductButtonClick(View view){
        Intent addProductIntent = new Intent(MainActivity.this, AddProduct.class);
        startActivity(addProductIntent);
    }
    public void ViewOrderButtonClick(View view){
        startActivity(new Intent(MainActivity.this,OpenOrdersActivity.class));
    }
    public void ViewProductButtonClick(View view){
        startActivity(new Intent(MainActivity.this,ViewProductActivity.class));
    }


}

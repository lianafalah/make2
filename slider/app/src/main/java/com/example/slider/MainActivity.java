package com.example.slider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class MainActivity extends AppCompatActivity {
    TextView lips;
    String var_lips;

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lips = (TextView) findViewById(R.id.Lips);

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Apa kalian ingin Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })

                .setNegativeButton("No", null)
                .show();
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Product) {
            startActivity(new Intent(this, MenuActivity.class));

        } else if (item.getItemId() == R.id.Logout) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        return true;
    }
    public void lipClick(View view){
        var_lips = lips.getText().toString();
        Intent i = null;
        i = new Intent(MainActivity.this,MenuActivity.class);
        Bundle b = new Bundle();
        b.putString("parse_nama", var_lips);
        i.putExtras(b);
        startActivity(i);

        /*
        final TextView lip = (TextView) findViewById(R.id.lips);
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("data1", lip.getText().toString());
        startActivity(intent);*/

    }
    public void eyeClick(View view){
        var_lips = lips.getText().toString();
        Intent i = null;
        i = new Intent(MainActivity.this,MenuActivity.class);
        Bundle b = new Bundle();
        b.putString("parse_nama", var_lips);
        i.putExtras(b);
        startActivity(i);
    }

}

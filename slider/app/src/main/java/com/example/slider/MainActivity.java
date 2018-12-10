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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar ;
    TextView lips,eye,face,skincare,tools,pallete;



    FirebaseAuth mAuth;
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Over");
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Make Up");

        lips = (TextView) findViewById(R.id.Lips);
        eye = (TextView) findViewById(R.id.Eye);
        face = (TextView) findViewById(R.id.Face);
        skincare = (TextView) findViewById(R.id.SkinCare);
        tools = (TextView) findViewById(R.id.Tools);
        pallete = (TextView) findViewById(R.id.ProPalette);
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
        mAuth = FirebaseAuth.getInstance();


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

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Product) {
            startActivity(new Intent(this, MenuActivity.class));

        } else if (item.getItemId() == R.id.Logout) {
            FirebaseAuth.getInstance().signOut();
        }
        return true;
    }

    public void lipClick(View view){
        String nama= lips.getText().toString();

        Intent myIntent = new Intent (MainActivity.this,
                MenuActivity.class);
        Bundle paket = new Bundle();
        paket.putString("nama",nama);

        myIntent.putExtras(paket);
        startActivity(myIntent);

    }
    public void eyeClick(View view){
        String nama= eye.getText().toString();

        Intent myIntent = new Intent (MainActivity.this,
                MenuActivity.class);
        Bundle paket = new Bundle();
        paket.putString("nama",nama);

        myIntent.putExtras(paket);
        startActivity(myIntent);

    }
    public void faceClick(View view){
        String nama= face.getText().toString();

        Intent myIntent = new Intent (MainActivity.this,
                MenuActivity.class);
        Bundle paket = new Bundle();
        paket.putString("nama",nama);

        myIntent.putExtras(paket);
        startActivity(myIntent);

    }
    public void skincareClick(View view){
        String nama= skincare.getText().toString();

        Intent myIntent = new Intent (MainActivity.this,
                MenuActivity.class);
        Bundle paket = new Bundle();
        paket.putString("nama",nama);

        myIntent.putExtras(paket);
        startActivity(myIntent);

    }
    public void toolsClick(View view){
        String nama= tools.getText().toString();

        Intent myIntent = new Intent (MainActivity.this,
                MenuActivity.class);
        Bundle paket = new Bundle();
        paket.putString("nama",nama);

        myIntent.putExtras(paket);
        startActivity(myIntent);

    }
    public void propalleteClick(View view){
        String nama= pallete.getText().toString();

        Intent myIntent = new Intent (MainActivity.this,
                MenuActivity.class);
        Bundle paket = new Bundle();
        paket.putString("nama",nama);

        myIntent.putExtras(paket);
        startActivity(myIntent);

    }


}

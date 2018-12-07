package com.example.addmakeover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MenuBottom extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_menu_bottom;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_home;
    }
    public void AddProductButtonClick(View view){
        Intent addProductIntent = new Intent(MenuBottom.this, AddProduct.class);
        startActivity(addProductIntent);
    }
    public void ViewOrderButtonClick(View view){
        startActivity(new Intent(MenuBottom.this,TwoActivity.class));
    }
    public void ViewProductButtonClick(View view){
        startActivity(new Intent(MenuBottom.this,TreeActivity.class));
    }
}

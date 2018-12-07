package com.example.addmakeover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TreeActivity extends BaseActivity {


    @Override
    int getContentViewId() {
        return R.layout.activity_tree;
    }

    @Override
    int getNavigationMenuItemId() {

        return R.id.navigation_notifications;
    }
}

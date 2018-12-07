package com.example.addmakeover;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import java.lang.reflect.Field;

public class BottomNavigationViewHelper {
    public static void disableShiftMode(BottomNavigationView view){
        BottomNavigationView menuView = (BottomNavigationView) view.getChildAt(0);
        try{
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i =0; i<menuView.getChildCount(); i++){
                BottomNavigationView item = (BottomNavigationView) menuView.getChildAt(i);
                //item.setSelected(boolean);
            }
        }
        catch (NoSuchFieldException e){

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
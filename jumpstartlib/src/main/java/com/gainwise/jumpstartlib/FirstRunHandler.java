package com.gainwise.jumpstartlib;

/*
   * An instance of this class will be created in the launcher activity -
   * If it is the first run of the app, it will execute a custom
   * block of code passed via an object implementing the FirstRunner interface
   * passed via the FirstRunHandler constructor
   * */

import android.content.Context;
import android.content.SharedPreferences;



//When constructing a FirstRunHandler instance, pass an anonymous or named object implementing this
//interface
interface FirstRunner{
    void execute();
}

public class FirstRunHandler {


   //when the object is constructed in the calling activity, it will then test via this constructor
   //whether it is the first run or not via shared preferences
    public FirstRunHandler(Context context, FirstRunner firstRunner) {
        SharedPreferences pref = context.getSharedPreferences("FIRSTRUN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String first = pref.getString("FirstRunKey", null);
        if (first == null) {
            firstRunner.execute();
            editor.putString("FirstRunKey", "no");
            editor.apply();
        }

    }
}



package com.gainwise.jumpstartlib;

/*
   An instance of this class will allow take all the grunt work from requesting permissions
   programmatically.

    1 - Make 2 methods - one you want to be called if permission is denied and another if permission is granted
    2 - Create inner class that implements PermissionsDirective, fill in all implemented members (you will link the methods from step 1 in this inner class's implemented members)
    3 - Create a class-wide helper variable of type PermissionsHandler, pass that inner class from step 2 to the constructor
    4 - After onCreate method - Override the onRequestPermissionsResult and replace super. with helper.handleResult (the super method is called from within the handleResult)
    5 - Finally, the helper's requestPermissions method will start the permission asking
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;



public class PermissionsHandler extends AppCompatActivity {

    //declare variables
    PermissionsDirective directive;
    Activity activity;
    String permissions[];
    int requestCode;

    //constructor to init variables
    public PermissionsHandler(PermissionsDirective directive, Activity activity, String[] permissions, int requestCode) {
        this.directive = directive;
        this.activity = activity;
        this.permissions = permissions;
        this.requestCode = requestCode;
    }


    //this method will return true or false if permissions are granted or not
    boolean needPermissions(String[] permissions){
        boolean show = true;
        for (int i=0; i< permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(directive.withActivity(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                show = false;
            }
        }
        return !show;
    }


    // this is the method that initiates asking the permissions
    void requestPermissions(){
        for (int i = 0 ; i<  permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
            }
        }
    }

    //this is the method that will handle the results of the permissions
    //the calling activity must override the onRequestPermissionsResult and
    //pass it to the PermissionsHandler object's method handleResult
    void handleResult(int requestCode, String[] permissions, int[] grantResults) {

        boolean allGranted = true;

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {
            if (grantResults.length > 0 && ActivityCompat.checkSelfPermission(activity,
                    permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }
        if(allGranted) {
            // all permissions are granted
            directive.executeOnPermissionGranted();
        }
        else {
            // if one was denied this will be called
            directive.executeOnPermissionDenied();

        }
    }



    //this is a bonus method to send the user to the OS settings of your application
    void sendToSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
        Toast.makeText(context, "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
    }

    //in the calling class - an anonymous or named object will implement this interface
    //and pass it via the constructor of the PermissionsHelper object when its created
    //all of the details of the permission being requested are in this
    interface PermissionsDirective{
        String[] permissionsToRequest();
        int requestCode();
        Activity withActivity();
        void executeOnPermissionGranted();
        void executeOnPermissionDenied();
    }

}

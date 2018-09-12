# JumpStart


<b>MIN SDK: 17</b><br>
<b>Written in Java - can be used in Kotlin and Java based Android projects</b><br><br>
The main purpose of this library is to easily jump start a new android app project by wrapping useful code functionality in custom objects, whether named or anonymous.<br><br>
<b>Three areas of focus to this library:</b><br>
Programmatically requesting permissions<br>
Code execution upon unhandled exceptions <br>
Code execution the first time an app runs on client's device<br><br><br>

<b>Bring into your project</b><br><br>
Add this into your root build.gradle file:<br>
```
 allprojects {		
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```
Add this into your module build.gradle file:<br>
```
 allprojects {
	dependencies {
	       implementation 'com.github.Gaineyj0349:JumpStart:8f4af41fd6'
	}
}
```

<b><U>PERMISSIONS</U></b>:<br>
Since api 21 we must programmatically ask for permissions with dangerous permissions. This library makes this process very easy.
<br><br>

From the calling activity, create an inner class that implements the PermissionsDirective interface and implement the interface's members. Then within the implemented methods, put in your own code specific to the permissions to request, as well as the actions to take if the permissions are granted or denied. The method names are self descriptive in terms of what they do:<br>
```
    class PermissionsHelper implements PermissionsHandler.PermissionsDirective{

        //You will place your own permissions here in a String array
        
        @Override
        public String[] permissionsToRequest() {
            String[] permissions = new String[4];
            permissions[0] = Manifest.permission.READ_PHONE_STATE;
            permissions[1] = Manifest.permission.READ_CONTACTS;
            permissions[2] = Manifest.permission.SEND_SMS;
            permissions[3] = Manifest.permission.RECEIVE_SMS;
            return permissions;
        }

        //this is the int code for this request process - any int you supply will be fine as long as
        //it is unique to permissions requests
        
        @Override
        public int requestCode() {
            return 21;
        }
        

        //pass the super activity
        
        @Override
        public Activity withActivity() {
            return MainActivity.this;
        }

        //I am simply displaying a toast here, but you can set up anything you would like
        //to execute. For example, you could create methods in MainActivity and call them
        //here
        @Override
        public void executeOnPermissionGranted() {
            Toast.makeText(MainActivity.this, "Permissions Granted", Toast.LENGTH_LONG).show();
        }

        //I am simply displaying a toast here, but you can set up anything you would like
        //to execute. For example, you could create methods in MainActivity and call them
        //here - for example - request permissions again
        @Override
        public void executeOnPermissionDenied() {
            Toast.makeText(MainActivity.this, "One of the Permissions was denied.", Toast.LENGTH_LONG).show();
        }
    }
```
<br>
Once this inner class is made, you will pass it as a parameter to the object of type PermissionsHandler. Lets do this now
<br>

In the activity, create a classwide (not within a lifecycle method) object of type PermissionsHandler.
```
//this classwide pHandler variable will be used to actually call/check/retrieve permissions transactions and requests
PermissionsHandler pHandler = new PermissionsHandler(new PermissionsHelper());
```
<br>
Now that we have a PermissionsHandler object that has been constructed with all the necessary permissions information- lets ask for the permission! The PermissionsHandler class has methods called needPermissions(), getPermissions(), and requestPermissions(). We can always make sure our permissions are secured before calling any code that would require dangerous permissions. Then once we request the permissions, we will also need to handle the result from the calling activity - but we can just pass this information to the pHandler onject via the method handleResult() like so:<br>

```
public class MainActivity extends AppCompatActivity {

    PermissionsHandler pHandler = new PermissionsHandler(new PermissionsHelper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    
 	//other code removed for brevity, and you can place the following if block anywhere you would like. For
	//example, if you only want the permission to be requested for a button click, use it in the listener. and
	// if the permission isn't granted it will ask, if it is already approved it will execute the code block
	//in the executeOnPermissionsGranted() method in the interface implementing object
 	
	   if(pHandler.needPermissions(pHandler.getPermissions())){
            pHandler.requestPermissions();
        }
	
    }
    
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        pHandler.handleResult(requestCode, permissions, grantResults);
    }
}

```

This wraps up the Permissions part of this library.

<b><U>UNHANDLED EXCEPTIONS</U></b>:<br>
<br><br>

Normally we like to capture exceptions in a try/catch block but sometimes we can not always anticipate when exceptions will be thrown. 
This can result in the app crashing, and when the app crashes from an unhandled exception - no lifecycle methods are called. So,
if you need a block of code to execute upon an app crash from an unhandled exception, we can use the CrashAllocator class and Crashable interface within this library to accomplish this like so:



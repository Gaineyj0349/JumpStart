# JumpStart


<b>MIN SDK: 17</b><br>
<b>Written in Java - can be used in Kotlin and Java based Android projects</b><br><br>
The main purpose of this library is to easily jump start a new android app project by wrapping useful code functionality in custom objects.<br><br>
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
```

<b>Permissions</b>:<br>
Since api 21 we must programmatically ask for permissions with dangerous permissions. This library makes this process very easy.
<br><br>

Create an inner class that implements the PermissionsDirective interface and implement the interface membes. Then within the implemented methods, put in your own code specific to the permissions to request, as well as the actions to take if the permissions are granted or denied. The method names are self descriptive in terms of what they do:<br>
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
        

        //pass this super activity with this method
        
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
        //here
        @Override
        public void executeOnPermissionDenied() {
            Toast.makeText(MainActivity.this, "One of the Permissions was denied.", Toast.LENGTH_LONG).show();
        }
    }
```


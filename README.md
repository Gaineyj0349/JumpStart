# JumpStart
[![](https://jitpack.io/v/Gaineyj0349/JumpStart.svg)](https://jitpack.io/#Gaineyj0349/JumpStart)

<b>MIN SDK: 17</b><br>
<b>Written in Java - can be used in Kotlin and Java based Android projects</b><br><br>
The main purpose of this library is to easily jump start a new android app project by wrapping useful code functionality in custom objects.<br><br>
<b>Three areas of focus to this library:</b><br>
Programmatically requesting permissions<br>
Code execution upon unhandled exceptions <br>
Code execution the first time an app runs on client's device<br><br><br>

<b>Bring into your project</b><br>
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
	        implementation 'com.github.Gaineyj0349:JumpStart:fe17bd6b58'
	}
```

How to easily programmatically ask for permissions:<br>



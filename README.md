# Android Utils
<p align="center">
  <img src="https://github.com/MicahSphelele/android-utils/blob/master/pics/android_logo.png" width="100" height="100">
</p>
An open source experimental repository for android libraries that will be compatible with Java and Kotlin  

How to get started 
--------------

1. Add jitpack.io to your root build.gradle file:

    ````groovy
    allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
    }
    ````
## Phone Code Picker 

Phone code picker is a simple and easy to use picker, which makes it easier to select and search for a country code for a mobile number. The library is supports API level 17 and up

<img src="https://github.com/MicahSphelele/android-utils/blob/master/pics/phonepicker/snippet.jpg" width="100" height="50">

Phone code picker will provide a proffesional and seamless look into your UI whether you want to use it for a sign up screen or a sign screen. 

<img src="https://github.com/MicahSphelele/android-utils/blob/master/pics/phonepicker/snippet_edit.jpg" width="300" height="90">

### How to add to project 
1. Add library to your project build.gradle file and then sync 
````groovy
	dependencies {
	   implementation 'com.github.MicahSphelele:android-utils:1.0.0-beta'
	}
````

 2. Add picker to layout using the following:

     ````xml
     <om.sphe.phonecodepicker.ui.PhoneCodePicker
           android:id="@+id/picker"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content" />
     ````
 3. Add EditText/AppCompatEditText view to layout:

     ````xml
     <androidx.appcompat.widget.AppCompatEditText
            android:id="@+id/phone_edit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="phone"
            android:inputType="phone"/>
     ````
4. Register the EditText/AppCompatEditText with code:

   ```java
   CountryCodePicker picker;
   AppCompatEditText phoneEdit;

   ...

   picker = findViewById(R.id.picker);
   phoneEdit = findViewById(R.id.phone_edit);

   ...

   picker.registerPhoneNumberTextView(phoneEdit);
   ```
   Some of the attributes that can be used in PhoneCodePicker layout:

|   Attribute    |   method                        | Description
|---------------|---------------------------------|-------------------------------
|picker_defaultCode | setDefaultCountryUsingPhoneCodeAndApply(int defaultCode) |  set selected Flag and phone in CCP by phone code.
|picker_showFullName| showFullName(boolean show) | Show full name of country in CCP. Default is false|
|picker_hideNameCode| hideNameCode(boolean hide) | Hide the country name code. Default is false|
|picker_hidePhoneCode| hidePhoneCode(boolean hide)| Hide the phone code. Default is false|
|picker_DialogMode| setDialogMode(int mode)| Set fullscreen mode or dialog mode (full_dialog or dialog)

Library Features
--------
What inspired me from re-wrtting this library was this feature

### 1. Dialog Mode
* You can pick how you want to view the list from countries by using two different modes full_dialog or dialog
* NB: dialog mode is alway default
  #### A. Through xml
   ##### Using dialog mode
     
     Add `app:picker_DialogMode="dialog"`
     
   ####  B. Programmatically
    ##### Using set dialog mode
    Use `setDialogMode(PhoneCodePicker.DIALOG_MODE_DIALOG)` method.

|   Full Dialog Mode    |   Dialog Mode           
|---------------|---------------------------------
|   <img src="https://github.com/MicahSphelele/android-utils/blob/master/pics/phonepicker/full_dialog.jpg" width="250" height="500">| <img src="https://github.com/MicahSphelele/android-utils/blob/master/pics/phonepicker/dialog.jpg" width="250" height="500">

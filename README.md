# Android Phone Code Picker
<p align="center">
  <img src="https://github.com/MicahSphelele/android-utils/blob/master/pics/android_logo.png" width="100" height="100">
</p>
An open source  android libraries that is compatible with Java and Kotlin  

Phone code picker is a simple and easy to use picker, which makes it easier to select and search for a country code for a mobile number. The library is supports API level 17 and up. The reason I wrote this library was for me to add a few new features and also implement a `RecyclerView` instead of `ListView`. The developement of this library was inspired by  [**Joielechong**](https://github.com/joielechong/)

<img src="https://github.com/MicahSphelele/android-utils/blob/master/pics/phonepicker/snippet.jpg" width="100" height="50">

Phone code picker will provide a proffesional and seamless look into your UI whether you want to use it for a sign up screen or a sign screen. 

<img src="https://github.com/MicahSphelele/android-utils/blob/master/pics/phonepicker/snippet_edit.jpg" width="300" height="90">


### How to add to project 
1. Add jitpack.io to your root build.gradle file:

    ````groovy
    allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
    }
    ````
    
2. Add library to your project build.gradle file and then sync 
````groovy
	dependencies {
	   implementation 'com.github.MicahSphelele:android-utils:1.1.1'
	}
````

 3. Add picker to layout using the following:

     ````xml
     <com.sphe.phonecodepicker.ui.PhoneCodePicker
           android:id="@+id/picker"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content" />
     ````
 4. Add EditText/AppCompatEditText view to layout:

     ````xml
     <androidx.appcompat.widget.AppCompatEditText
            android:id="@+id/phone_edit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="phone"
            android:inputType="phone"/>
     ````
5. Register the EditText/AppCompatEditText with code:

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
What inspired me from re-wrtting this library was these features.

### 1. Dialog Mode
* You can pick how you want to view the list from countries by using two different modes `full_dialog` or `dialog`
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

### 2. Dialog Adaptive OS Theme (DARK MODE) support
 * You can set the library dialogs to adapt colors if os is using dark mode
 * NB: this will only work on devices using API level 29> and ensure that you don't interfere with default colors when using this attribute 
 #### A. Through xml
 ##### Using os theme support
 
 Add `app:picker_supportOSTheme="true"`
 
 ####  B. Programmatically
   ##### Using os theme support
   Use `setIsSupportOSTheme(true)` method.
   
   |   OS in dark mode            
|---------------
|   <img src="https://github.com/MicahSphelele/android-utils/blob/master/pics/phonepicker/dialg_dark.jpg" width="250" height="500">| 

   
   ### More about features
   
  * Should you feel that you need more info about the features please ref : [**CountryCodePicker**](https://github.com/joielechong/CountryCodePicker/)
 
 ### For DexGuard users
If your project is obfuscated with DexGuard you may need to add the following line to the DexGuard configuration:
      '`-keepresourcefiles assets/io/michaelrocks/libphonenumber/android/**`
      This is because this library use [libphonenumber-android](https://github.com/MichaelRocks/libphonenumber-android)

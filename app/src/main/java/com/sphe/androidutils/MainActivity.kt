package com.sphe.androidutils

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity

import com.sphe.phonecodepicker.ui.PhoneCodePicker

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "@MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        picker.dialogMode = PhoneCodePicker.DIALOG_MODE_DIALOG
        btnSwitch.isChecked = true

        text.text = String.format("%s | %s",picker.selectedCountryName,picker.selectedCountryCode)

        picker.registerPhoneNumberTextView(edtText)
        picker.setOnCountryChangeListener {country ->
            text.text = String.format("%s | %s",country.name,country.phoneCode)
            Log.d(TAG,country.name)
        }

        btnSwitch.setOnCheckedChangeListener { _, isChecked ->
            when(isChecked){
                true ->{
                    picker.dialogMode = PhoneCodePicker.DIALOG_MODE_DIALOG
                }
                false->{
                    picker.dialogMode = PhoneCodePicker.DIALOG_MODE_FULL
                }
            }
        }
    }
}

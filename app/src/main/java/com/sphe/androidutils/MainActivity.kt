package com.sphe.androidutils

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.sphe.phonecodepicker.ui.CountryCodePicker

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "@MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countryPicker.dialogMode = CountryCodePicker.DIALOG_MODE_DIALOG
        btnSwitch.isChecked = true

        text.text = String.format("%s | %s",countryPicker.selectedCountryName,countryPicker.selectedCountryCode)

        countryPicker.registerPhoneNumberTextView(edtText)
        countryPicker.setOnCountryChangeListener {country ->
            text.text = String.format("%s | %s",country.name,country.phoneCode)
        }

        btnSwitch.setOnCheckedChangeListener { _, isChecked ->
            when(isChecked){
                true ->{
                    countryPicker.dialogMode = CountryCodePicker.DIALOG_MODE_DIALOG
                }
                false->{
                    countryPicker.dialogMode = CountryCodePicker.DIALOG_MODE_FULL
                }
            }
        }
        
    }
}

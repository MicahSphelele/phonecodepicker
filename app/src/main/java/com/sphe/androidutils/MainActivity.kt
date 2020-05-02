package com.sphe.androidutils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "@MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text.text = String.format("%s | %s",countryPicker.selectedCountryName,countryPicker.selectedCountryCode)
        countryPicker.registerPhoneNumberTextView(edtText)

        countryPicker.setOnCountryChangeListener {country ->
            text.text = String.format("%s | %s",country.name,country.phoneCode)
        }
    }
}

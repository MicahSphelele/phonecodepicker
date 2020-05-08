package com.sphe.androidutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sphe.phonecodepicker.ui.CountryCodePicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countryPicker.dialogMode = CountryCodePicker.DIALOG_MODE_DIALOG;
    }
}

package com.example.testingapp

import android.os.Bundle
import com.example.testingapp.base.BaseActivity

class TampilanAwal : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tampilan_awal)

        //goBasicSpecial(TestPageLogin::class.java) // For optimize leasingapps

        goBasicSpecial(MainActivity5::class.java) // For Exercise Dicoding
    }

    override fun onBackPressed() {

    }
}
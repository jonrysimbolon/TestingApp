package com.example.testingapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TampilanAwal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tampilan_awal)

        goBasicSpecial(DicodingTest::class.java) // For Exercise Dicoding
    }

    private fun goBasicSpecial(activity: Class<*>) {
        val intent = Intent(this,activity)
        startActivity(intent)
        overridePendingTransition(0,0)
        finish()
    }

    override fun onBackPressed() {

    }
}
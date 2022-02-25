package com.example.testingapp

import com.example.testingapp.VariableCons.Companion.STATE_RESULT
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.compose.runtime.Immutable
import com.example.testingapp.base.BaseActivity
import com.example.testingapp.databinding.ActivityMain4Binding

class MainActivity5 : BaseActivity() {

    lateinit var binding: ActivityMain4Binding
    var tag = "Mainactivity5"
    var posisi = 1

    var dataBooks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        dataBooks = resources.getStringArray(R.array.listBooks).toMutableList()


    }

}
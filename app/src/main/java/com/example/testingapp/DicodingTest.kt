package com.example.testingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testingapp.databinding.ActivityDicodingTestBinding

class DicodingTest : AppCompatActivity() {

    lateinit var binding: ActivityDicodingTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDicodingTestBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
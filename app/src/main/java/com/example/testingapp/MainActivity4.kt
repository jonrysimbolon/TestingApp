package com.example.testingapp

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.testingapp.VariableCons.Companion.STATE_RESULT
import com.example.testingapp.base.BaseActivity
import com.example.testingapp.databinding.ActivityMain3Binding

class MainActivity4 : BaseActivity() {

    lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if(putData("statusLigth") == "Light"){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.switchNigthLight.isChecked = false
        }else if(putData("statusLigth") == "Night"){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.switchNigthLight.isChecked = true
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.switchNigthLight.isChecked = false
        }

        binding.switchNigthLight.setOnClickListener {
            if(!binding.switchNigthLight.isChecked){
                editDataPerson("statusLigth","Light")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }else{
                editDataPerson("statusLigth","Night")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}
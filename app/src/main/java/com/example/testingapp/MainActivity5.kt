package com.example.testingapp

import com.example.testingapp.VariableCons.Companion.STATE_RESULT
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import androidx.compose.runtime.Immutable
import androidx.core.content.ContextCompat
import com.example.testingapp.base.BaseActivity
import com.example.testingapp.databinding.ActivityMain4Binding
import com.orhanobut.dialogplus.DialogPlus

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

        binding.bukuBox.setOnClickListener {
            //val listAdapter = ListAdapter(this, dataBooks)
            val listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataBooks)
            val dialog = DialogPlus.newDialog(this)
                .setAdapter(listAdapter)
                .setOnItemClickListener { dialog, item, view, position ->
                    dialog.dismiss()
                    binding.bukuBox.setText(dataBooks[position])
                }
                .setExpanded(false)
                .setPadding(10, 20, 10, 20)
                .create()

            when(resources.getString(R.string.mode)){
                "Day" -> {
                    dialog.findViewById(R.id.dialogplus_outmost_container).setBackgroundColor(ContextCompat.getColor(this,R.color.white))
                }
                "Night" -> {
                    dialog.findViewById(R.id.dialogplus_outmost_container).setBackgroundColor(ContextCompat.getColor(this,R.color.black))
                }
            }

            dialog.show()
        }
    }

}
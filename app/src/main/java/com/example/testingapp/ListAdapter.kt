package com.example.testingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdapter(var context: Context, var listArray: MutableList<String>) : BaseAdapter() {

    private val inflter: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val convertView = inflter.inflate(R.layout.list_simple, null)
        val text1 = convertView.findViewById(R.id.text1) as TextView
        text1.text = listArray[position]
        return convertView
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listArray.size
    }

}

package com.bignerdranch.android.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var addButton: Button
    private lateinit var recycleView: RecyclerView
    private lateinit var image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.imageWithoutMovies)
        recycleView = findViewById(R.id.recView)
        var adapter: MainAdapter
        addButton = findViewById(R.id.buttonAdd)
        addButton.setOnClickListener(){

            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }
    }
}
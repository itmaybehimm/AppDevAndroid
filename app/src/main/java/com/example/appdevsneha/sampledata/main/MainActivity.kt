package com.example.appdevsneha.sampledata.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appdevsneha.R
import com.example.appdevsneha.data.db.NoteDatabase
import com.example.appdevsneha.data.repository.NoteRepository
import com.example.appdevsneha.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var db: NoteDatabase
    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        db = NoteDatabase.getDatabaseInstance(applicationContext)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        populate_notes()

    }

    fun populate_notes(){
        val notes = NoteRepository(db.noteDao()).readAllNotes
        notes.observe(this, Observer { notes ->
            // Update UI or access individual notes here
            if (notes != null) {
                binding.recyclerView.apply {
                    layoutManager=GridLayoutManager(applicationContext,2)
                    adapter= MainCardAdapter(notes)
                }
            }
        })
    }
}
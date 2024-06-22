package com.example.appdevsneha.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appdevsneha.R
import com.example.appdevsneha.activity.edit_note.EditNote
import com.example.appdevsneha.data.db.NoteDatabase
import com.example.appdevsneha.data.repository.NoteRepository
import com.example.appdevsneha.data.repository.NoteViewModel
import com.example.appdevsneha.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var db: NoteDatabase
    private lateinit var  binding: ActivityMainBinding
    private lateinit var addButton:FloatingActionButton
    private lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = NoteDatabase.getDatabaseInstance(applicationContext)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        populate_notes()
        add_listeners();

    }

    fun populate_notes(){
        val notes = NoteRepository(db.noteDao()).readAllNotes
        notes.observe(this, Observer { notes ->
            if (notes != null) {
                binding.recyclerView.apply {
                    layoutManager=GridLayoutManager(applicationContext,2)
                    adapter= MainCardAdapter(notes,{ note ->
                        val intent = Intent(this@MainActivity, EditNote::class.java)
                        intent.putExtra("NOTE_ID", note.id)
                        startActivity(intent)
                    },{note->
                        noteViewModel.deleteNote(note)
                    })
                }
            }
        })
    }

    fun add_listeners(){
        addButton = findViewById(R.id.addButton)

        addButton.setOnClickListener{
            val intent = Intent(
                this,
                EditNote::class.java
            )
            startActivity(intent)
        }
    }
}
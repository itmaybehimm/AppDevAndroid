package com.example.appdevsneha.activity.edit_note

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appdevsneha.R
import com.example.appdevsneha.data.model.Note
import com.example.appdevsneha.data.repository.NoteRepository
import com.example.appdevsneha.data.repository.NoteViewModel
import com.google.android.material.textfield.TextInputEditText

class EditNote : AppCompatActivity() {
    private lateinit var titleInput:EditText
    private lateinit var bodyInput:EditText
    private lateinit var note:Note
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var saveButton:Button
    private lateinit var discardButton:Button
    private var noteId: Int = -1
    private var isNewNote = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        titleInput = findViewById(R.id.titleInput)
        bodyInput = findViewById(R.id.bodyInput)

        saveButton=findViewById(R.id.saveButton)
        discardButton=findViewById(R.id.discardButton)

        noteId = intent.getIntExtra("NOTE_ID", -1)
        isNewNote = noteId == -1

        if (!isNewNote) {
            getNote()
        }
        addListeners()
    }
    private fun getNote() {
        noteViewModel.getNoteById(noteId).observe(this, Observer { note ->
            note?.let {
                titleInput.setText(it.title)
                bodyInput.setText(it.body)
            }
        })
    }

    private fun saveNote() {
        val title = titleInput.text.toString()
        val body = bodyInput.text.toString()
        val note = if (isNewNote) {
            Note(
                title = title,
                body = body
            )
        } else {
            Note(
                id = noteId,
                title = title,
                body = body
            )
        }

        if (isNewNote) {
            noteViewModel.addNote(note)
        } else {
            noteViewModel.updateNote(note)
        }
        finish()
    }

    private fun addListeners() {
        saveButton.setOnClickListener{saveNote()}
        discardButton.setOnClickListener{finish()}
    }
}
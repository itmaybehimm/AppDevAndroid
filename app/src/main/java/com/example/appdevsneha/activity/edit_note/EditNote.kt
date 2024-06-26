package com.example.appdevsneha.activity.edit_note

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appdevsneha.R
import com.example.appdevsneha.data.db.NoteDatabase
import com.example.appdevsneha.data.model.Folder
import com.example.appdevsneha.data.model.Note
import com.example.appdevsneha.data.repository.FolderViewModel
import com.example.appdevsneha.data.repository.NoteRepository
import com.example.appdevsneha.data.repository.NoteViewModel
import com.google.android.material.textfield.TextInputEditText

class EditNote : AppCompatActivity() {
    private lateinit var titleInput:EditText
    private lateinit var bodyInput:EditText
    private lateinit var note:Note
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var folderViewModel: FolderViewModel
    private lateinit var saveButton:Button
    private lateinit var discardButton:Button
    private lateinit var chooseFolderButton:Button
    private var noteId: Int = -1
    private var folderId: Int? = null
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
        folderViewModel=ViewModelProvider(this).get(FolderViewModel::class.java)

        titleInput = findViewById(R.id.titleInput)
        bodyInput = findViewById(R.id.bodyInput)

        saveButton=findViewById(R.id.saveButton)
        discardButton=findViewById(R.id.discardButton)
        chooseFolderButton=findViewById(R.id.chooseFolderButton)

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
                folderId = it.folderId
                if (folderId != null) {
                    folderViewModel.getFolderById(folderId!!).observe(this, Observer { folder ->
                        folder?.let {
                            chooseFolderButton.background.setTint(ContextCompat.getColor(this, R.color.accent_1))
                            chooseFolderButton.text = folder.name
                        }
                    })
                } else {
                    chooseFolderButton.background.setTint(ContextCompat.getColor(this, R.color.onBackground))
                    chooseFolderButton.text = "Folder"
                }
            }
        })
    }

    private fun saveNote(exit:Boolean=true) {
        val title = titleInput.text.toString()
        val body = bodyInput.text.toString()
        val note = if (isNewNote) {
            Note(
                title = title,
                body = body,
                folderId = folderId
            )
        } else {
            Note(
                id = noteId,
                title = title,
                body = body,
                folderId = folderId
            )
        }

        if (isNewNote) {
            noteViewModel.addNote(note)
            isNewNote=false
        } else {
            noteViewModel.updateNote(note)
        }
        if(exit) {
            finish()
        }
    }

    private fun addListeners() {
        saveButton.setOnClickListener{saveNote()}
        discardButton.setOnClickListener{finish()}
        chooseFolderButton.setOnClickListener{showChooseFolderDialog()}
    }

    private fun showChooseFolderDialog() {
        val folderLiveData: LiveData<List<Folder>> = NoteDatabase.getDatabaseInstance(this).folderDao().readAllFolders()
        folderLiveData.observe(this, Observer { folders ->
            val builder = AlertDialog.Builder(this)

            val dialogLayout = layoutInflater.inflate(R.layout.dialog_choose_folder, null)
            val folderList = dialogLayout.findViewById<LinearLayout>(R.id.folderList)

            folderList.removeAllViews()

            folders.forEach { folder ->
                val folderButton = Button(this)
                folderButton.text = folder.name
                folderButton.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                folderButton.setOnClickListener {
                    folderId = folder.id
                    chooseFolderButton.background.setTint(ContextCompat.getColor(this, R.color.accent_1))
                    chooseFolderButton.setText(folder.name)
                    saveNote(exit = false)

                }
                folderList.addView(folderButton)
            }
            val folderButton = Button(this)
            folderButton.text = "Remove from Folder"
            folderButton.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            folderButton.setOnClickListener {
                folderId=null
                chooseFolderButton.background.setTint(ContextCompat.getColor(this,R.color.onBackground))
                chooseFolderButton.setText("Folder")
                saveNote(exit = false)
            }
            folderList.addView(folderButton)
            builder.setView(dialogLayout)

            builder.show()
        })
    }



}
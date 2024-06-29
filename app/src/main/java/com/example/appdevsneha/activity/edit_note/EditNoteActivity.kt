package com.example.appdevsneha.activity.edit_note

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.appdevsneha.R
import com.example.appdevsneha.Utils
import com.example.appdevsneha.data.db.NoteDatabase
import com.example.appdevsneha.data.model.Folder
import com.example.appdevsneha.data.model.Note
import com.example.appdevsneha.data.model.User
import com.example.appdevsneha.data.repository.FolderViewModel
import com.example.appdevsneha.data.repository.NoteViewModel

class EditNoteActivity : AppCompatActivity() {
    private lateinit var titleInput:EditText
    private lateinit var bodyInput:EditText
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var folderViewModel: FolderViewModel
    private lateinit var saveButton:Button
    private lateinit var discardButton:Button
    private lateinit var chooseFolderButton:Button
    private var noteId: Int = -1
    private var folderId: Int? = null
    private var isNewNote = true
    private var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        user = Utils.getUser(this)

        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        folderViewModel= ViewModelProvider(this)[FolderViewModel::class.java]

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
        noteViewModel.getNoteById(noteId, user!!.id).observe(this) { note ->
            note?.let {
                titleInput.setText(it.title)
                bodyInput.setText(it.body)
                folderId = it.folderId
                if (folderId != null) {
                    folderViewModel.getFolderById(folderId!!,user!!.id).observe(this) { folder ->
                        folder?.let {
                            chooseFolderButton.background.setTint(
                                ContextCompat.getColor(
                                    this,
                                    R.color.accent_1
                                )
                            )
                            chooseFolderButton.text = folder.name
                        }
                    }
                } else {
                    chooseFolderButton.background.setTint(
                        ContextCompat.getColor(
                            this,
                            R.color.onBackground
                        )
                    )
                    chooseFolderButton.text = buildString {
        append("Folder")
    }
                }
            }
        }
    }

    private fun saveNote(exit:Boolean=true) {
        val title = titleInput.text.toString()
        val body = bodyInput.text.toString()
        val note = if (isNewNote) {
            Note(
                title = title,
                body = body,
                folderId = folderId,
                userId = user!!.id
            )
        } else {
            Note(
                id = noteId,
                title = title,
                body = body,
                folderId = folderId,
                userId=user!!.id
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
        val folderLiveData: LiveData<List<Folder>> = NoteDatabase.getDatabaseInstance(this).folderDao().readAllFolders(user!!.id)
        folderLiveData.observe(this) { folders ->
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
                    chooseFolderButton.background.setTint(
                        ContextCompat.getColor(
                            this,
                            R.color.accent_1
                        )
                    )
                    chooseFolderButton.text = folder.name


                }
                folderList.addView(folderButton)
            }
            val folderButton = Button(this)
            folderButton.text = buildString {
        append("Remove from Folder")
    }
            folderButton.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            folderButton.setOnClickListener {
                folderId = null
                chooseFolderButton.background.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.onBackground
                    )
                )
                chooseFolderButton.text = buildString {
                    append("Folder")
                }

            }
            folderList.addView(folderButton)
            builder.setView(dialogLayout)

            builder.show()
        }
    }



}
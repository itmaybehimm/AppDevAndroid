package com.example.appdevsneha.activity.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appdevsneha.MyApp
import com.example.appdevsneha.R
import com.example.appdevsneha.Utils
import com.example.appdevsneha.activity.edit_note.EditNoteActivity
import com.example.appdevsneha.activity.quiz.QuizMainActivity
import com.example.appdevsneha.data.db.NoteDatabase
import com.example.appdevsneha.data.model.Folder
import com.example.appdevsneha.data.model.Note
import com.example.appdevsneha.data.model.User
import com.example.appdevsneha.data.repository.FolderViewModel
import com.example.appdevsneha.data.repository.NoteViewModel
import com.example.appdevsneha.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {
    private lateinit var db: NoteDatabase
    private lateinit var  binding: ActivityMainBinding
    private lateinit var addButton:FloatingActionButton
    private lateinit var folderAddButton:FloatingActionButton
    private lateinit var quizButton:FloatingActionButton
    private lateinit var profileButton:FloatingActionButton
    private lateinit var folderUpdateButton:FloatingActionButton
    private lateinit var notes:LiveData<List<Note>>
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var folderViewModel: FolderViewModel
    private lateinit var folderContainer: LinearLayout
    private lateinit var searchView: SearchView
    private var currentSearchString: String? = ""
    private var currentFolderId: Int? = null
    private var user: User? = null

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
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        folderContainer = findViewById(R.id.folderContainer)
        searchView = findViewById(R.id.seachBar)

        user =Utils.getUser(this)

        folderViewModel = ViewModelProvider(this)[FolderViewModel::class.java]

        folderViewModel.readAllFolders(user!!.id).observe(this) { folders ->
            folders?.let { populateFolders(it) }
        }

        populateNotes()
        addListeners()
        setupSearch()

    }



    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentSearchString=newText
                populateNotes()
                return true
            }
        })
    }


    fun populateNotes(){
        val searchString = currentSearchString ?: ""
        notes = when {
            currentFolderId != null && searchString.isEmpty() -> {
                noteViewModel.getNotesByFolderId(currentFolderId!!,user!!.id)
            }
            currentFolderId == null && searchString.isNotEmpty() -> {
                noteViewModel.searchNotes("%$searchString%",user!!.id)
            }
            currentFolderId != null && searchString.isNotEmpty() -> {
                noteViewModel.searchNotesInFolder("%$searchString%", currentFolderId!!,user!!.id)
            }
            else -> noteViewModel.readAllNotes(user!!.id)
        }

        notes.observe(this) { notes ->
            if (notes != null) {
                binding.recyclerView.apply {
                    layoutManager = GridLayoutManager(applicationContext, 2)
                    adapter = MainCardAdapter(notes, { note ->
                        val intent = Intent(this@MainActivity, EditNoteActivity::class.java)
                        intent.putExtra("NOTE_ID", note.id)
                        startActivity(intent)
                    }, { note ->
                        noteViewModel.deleteNote(note)
                    })
                }
            }
        }
    }
    private fun populateFolders(folders: List<Folder>) {
        folderContainer.removeAllViews()

        val buttonLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(16, 8, 16, 8) // Set margins
        }

        val allButton = Button(this).apply {
            text = buildString {
        append("All")
    }
            layoutParams = buttonLayoutParams
            setBackgroundResource(R.drawable.rounded_rectangle)
            setTextColor(resources.getColor(android.R.color.white))
            setPadding(16, 8, 16, 8)
            textSize = 10f
            setOnClickListener {
                clearColor()
                background.setTint(ContextCompat.getColor(applicationContext, R.color.accent_1))
                currentFolderId = null
                populateNotes()
            }
        }
        folderContainer.addView(allButton)

        // Dynamically add folder buttons
        folders.forEach { folder ->
            val button = Button(this).apply {
                text = folder.name
                layoutParams = buttonLayoutParams
                setBackgroundResource(R.drawable.rounded_rectangle)
                setTextColor(resources.getColor(android.R.color.white))
                setPadding(16, 8, 16, 8)
                textSize = 10f
                setOnClickListener {
                    clearColor()
                    currentFolderId = folder.id
                    background.setTint(ContextCompat.getColor(applicationContext, R.color.accent_1))
                    populateNotes()
                }
            }
            folderContainer.addView(button)
        }

    }

    private fun showAddFolderDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_folder, null)
        val folderNameInput = dialogView.findViewById<TextInputEditText>(R.id.folderNameInput)

        MaterialAlertDialogBuilder(this)
            .setTitle("Add Folder")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val folderName = folderNameInput.text.toString()
                if (folderName.isNotEmpty()) {
                    val newFolder = Folder(name = folderName, userId = user!!.id)
                    folderViewModel.addFolder(newFolder)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun addListeners(){
        addButton = findViewById(R.id.addButton)
        folderAddButton=findViewById(R.id.folderAddButton)
        quizButton=findViewById(R.id.quizButton)
        profileButton=findViewById(R.id.profileButton)
        folderUpdateButton=findViewById(R.id.folderUpdateButton)

        addButton.setOnClickListener{
            val intent = Intent(
                this,
                EditNoteActivity::class.java
            )
            startActivity(intent)
        }

        folderAddButton.setOnClickListener{
            showAddFolderDialog()
        }

        quizButton.setOnClickListener{
            val intent = Intent(
                this,
                QuizMainActivity::class.java
            )
            startActivity(intent)
        }
        profileButton.setOnClickListener{
            val intent = Intent(
                this,
                ProfileUpdateActivity::class.java
            )
            startActivity(intent)
        }




    }


    private fun clearColor() {
        for (i in 0 until folderContainer.childCount) {
            val child = folderContainer.getChildAt(i)
            if (child is Button) {
                child.backgroundTintList = null
            }
        }
    }
}
package com.example.appdevsneha.data.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.NoteDatabase
import com.example.appdevsneha.data.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application):AndroidViewModel(application) {
    private val readAllData:LiveData<List<Note>>
    private val repository: NoteRepository
    protected val scope = CoroutineScope(Dispatchers.Main)
    init{
        val noteDao = NoteDatabase.getDatabaseInstance(application).noteDao()
        repository = NoteRepository(noteDao)
        readAllData = repository.readAllNotes
    }

    fun addNote(note: Note){
        scope.launch {
            repository.addNote(note)
        }
    }


}
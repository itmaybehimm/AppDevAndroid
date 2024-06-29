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
    private val repository: NoteRepository
    protected val scope = CoroutineScope(Dispatchers.Main)
    init{
        val noteDao = NoteDatabase.getDatabaseInstance(application).noteDao()
        repository = NoteRepository(noteDao)

    }

    fun readAllNotes(userId: Int):LiveData<List<Note>>{
        return repository.readAllNotes(userId)
    }

    fun addNote(note: Note){
        scope.launch {
            repository.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        scope.launch {
            repository.updateNote(note)
        }

    }

    fun getNoteById(noteId: Int, userId:Int): LiveData<Note> {
        return repository.getNoteById(noteId, userId)
    }

    fun getNotesByFolderId(folderId:Int, userId:Int):LiveData<List<Note>>{
        return  repository.getNoteByFolderId(folderId, userId)
    }

    fun searchNotes(searchQuery:String, userId:Int):LiveData<List<Note>>{
        return repository.searchNotes(searchQuery, userId)
    }

    fun searchNotesInFolder(searchQuery:String,folderId:Int, userId:Int):LiveData<List<Note>>{
        return repository.searchNotesInFolder(searchQuery,folderId, userId)
    }
    fun deleteNote(note: Note) {
        scope.launch {
            repository.deleteNote(note)
        }

    }

}
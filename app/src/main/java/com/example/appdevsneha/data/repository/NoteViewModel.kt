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

    fun readAllNotes():LiveData<List<Note>>{
        return readAllData
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

    fun getNoteById(noteId: Int): LiveData<Note> {
        return repository.getNoteById(noteId)
    }

    fun getNotesByFolderId(folderId:Int):LiveData<List<Note>>{
        return  repository.getNoteByFolderId(folderId)
    }

    fun searchNotes(searchQuery:String):LiveData<List<Note>>{
        return repository.searchNotes(searchQuery)
    }

    fun searchNotesInFolder(searchQuery:String,folderId:Int):LiveData<List<Note>>{
        return repository.searchNotesInFolder(searchQuery,folderId)
    }
    fun deleteNote(note: Note) {
        scope.launch {
            repository.deleteNote(note)
        }

    }

}
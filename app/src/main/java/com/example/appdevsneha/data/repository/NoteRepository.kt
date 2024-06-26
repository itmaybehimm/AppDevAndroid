package com.example.appdevsneha.data.repository

import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.NoteDao
import com.example.appdevsneha.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {
    val readAllNotes:LiveData<List<Note>> = noteDao.readAllNotes()
    suspend fun addNote(note: Note){
        noteDao.insertNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    fun getNoteById(noteId: Int): LiveData<Note> {
        return noteDao.getNoteById(noteId)
    }

    fun getNoteByFolderId(folderId:Int):LiveData<List<Note>>{
        return noteDao.getNoteByFolderId(folderId)
    }

}
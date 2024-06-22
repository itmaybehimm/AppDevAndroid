package com.example.appdevsneha.data.repository

import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.NoteDao
import com.example.appdevsneha.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {
    val readAllNotes:LiveData<List<Note>> = noteDao.readAllNotes()
    suspend fun addNote(note: Note){
        noteDao.upsertNote(note)
    }

}
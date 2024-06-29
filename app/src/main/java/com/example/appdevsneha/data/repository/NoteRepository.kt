package com.example.appdevsneha.data.repository

import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.NoteDao
import com.example.appdevsneha.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {
    fun readAllNotes(userId: Int):LiveData<List<Note>> {
        return noteDao.readAllNotes(userId)
    }

    suspend fun addNote(note: Note){
        noteDao.insertNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    fun getNoteById(noteId: Int,userId:Int): LiveData<Note> {
        return noteDao.getNoteById(noteId,userId)
    }

    fun getNoteByFolderId(folderId:Int,userId:Int):LiveData<List<Note>>{
        return noteDao.getNoteByFolderId(folderId,userId)
    }

    fun searchNotes(searchQuery:String,userId:Int):LiveData<List<Note>>{
        return noteDao.searchNotes(searchQuery,userId)
    }

    fun searchNotesInFolder(searchQuery:String,folderId:Int,userId:Int):LiveData<List<Note>>{
        return noteDao.searchNotesInFolder(searchQuery,folderId,userId)
    }

}
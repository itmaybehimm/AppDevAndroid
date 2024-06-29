package com.example.appdevsneha.data.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.NoteDatabase
import com.example.appdevsneha.data.model.Folder
import com.example.appdevsneha.data.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FolderViewModel(application: Application): AndroidViewModel(application) {
    private val repository: FolderRepository
    protected val scope = CoroutineScope(Dispatchers.Main)
    init{
        val folderDao = NoteDatabase.getDatabaseInstance(application).folderDao()
        repository = FolderRepository(folderDao)
    }

    fun addFolder(folder: Folder){
        scope.launch {
            repository.addFolder(folder)
        }
    }

    fun updateFolder(folder: Folder) {
        scope.launch {
            repository.updateFolder(folder)
        }

    }

    fun deleteFolder(folder: Folder) {
        scope.launch {
            repository.deleteFolder(folder)
        }
    }

    fun readAllFolders(userId:Int):LiveData<List<Folder>>{
        return repository.readAllFolders(userId)
    }

    fun getFolderById(folderId:Int,userId:Int):LiveData<Folder>{
        return repository.getFolderById(folderId,userId)
    }

}
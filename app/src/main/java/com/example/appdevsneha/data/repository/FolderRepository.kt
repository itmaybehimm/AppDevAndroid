package com.example.appdevsneha.data.repository

import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.FolderDao
import com.example.appdevsneha.data.model.Folder

class FolderRepository(private val folderDao: FolderDao) {
    val readAllFolders:LiveData<List<Folder>> = folderDao.readAllFolders()
    suspend fun addFolder(folder: Folder){
        folderDao.addFolder(folder)
    }

    suspend fun updateFolder(folder: Folder) {
        folderDao.updateFolder(folder)
    }

    suspend fun deleteFolder(folder: Folder) {
        folderDao.deleteFolder(folder)
    }

    fun getFolderById(folderId: Int): LiveData<Folder> {
        return folderDao.getFolderById(folderId)
    }


}
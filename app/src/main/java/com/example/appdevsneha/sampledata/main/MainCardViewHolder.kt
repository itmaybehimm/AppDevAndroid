package com.example.appdevsneha.sampledata.main

import androidx.recyclerview.widget.RecyclerView
import com.example.appdevsneha.data.model.Note
import com.example.appdevsneha.databinding.MainCardCellBinding

class MainCardViewHolder(private val cellBinding: MainCardCellBinding):RecyclerView.ViewHolder(cellBinding.root) {
    fun bindNote(note: Note){
        cellBinding.cardNoteTitle.text = note.title
        cellBinding.cardNoteBody.text = note.body
    }
}
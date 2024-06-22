package com.example.appdevsneha.activity.main

import androidx.recyclerview.widget.RecyclerView
import com.example.appdevsneha.data.model.Note
import com.example.appdevsneha.databinding.MainCardCellBinding

class MainCardViewHolder(private val cellBinding: MainCardCellBinding):RecyclerView.ViewHolder(cellBinding.root) {
    fun bindNote(note: Note, clickListener: (Note) -> Unit,clickListenerDelete: (Note) -> Unit){
        cellBinding.cardNoteTitle.text = note.title
        cellBinding.cardNoteBody.text = note.body
        cellBinding.root.setOnClickListener{ clickListener(note) }
        cellBinding.deleteButton.setOnClickListener{clickListenerDelete(note)}
    }
}
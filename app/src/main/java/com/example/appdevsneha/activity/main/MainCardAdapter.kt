package com.example.appdevsneha.activity.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appdevsneha.data.model.Note
import com.example.appdevsneha.databinding.MainCardCellBinding

class MainCardAdapter(
    private val notes: List<Note>,
    private val clickListener: (Note) -> Unit,
    private val clickListenerDelete: (Note) -> Unit
):RecyclerView.Adapter<MainCardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding=MainCardCellBinding.inflate(from,parent,false)
        return MainCardViewHolder(binding)
    }

    override fun getItemCount(): Int =notes.size

    override fun onBindViewHolder(holder: MainCardViewHolder, position: Int) {
        holder.bindNote(notes[position],clickListener,clickListenerDelete)
//
//        holder.itemView.setOnClickListener{
//            clickListener(notes[position])
//        }
    }
}
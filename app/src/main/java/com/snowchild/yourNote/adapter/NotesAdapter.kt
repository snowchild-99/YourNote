package com.snowchild.yourNote.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.snowchild.yourNote.R
import com.snowchild.yourNote.model.NotesData
import java.util.ArrayList
import kotlin.random.Random

class NotesAdapter(
    private val context: Context,
    private val iNoteItemClickListener: INoteItemClickListener
) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notesList =ArrayList<NotesData> ()
    private val fullList = ArrayList<NotesData> ()

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteContainerLayout: CardView = itemView.findViewById(R.id.noteContainerLayout)
        val noteTitle: AppCompatTextView = itemView.findViewById(R.id.NotesTitle)
        val actualNote: AppCompatTextView = itemView.findViewById(R.id.actual_note)
        val date: AppCompatTextView = itemView.findViewById(R.id.date)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycleview_childitems, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.noteTitle.text = currentNote.title

        holder.noteTitle.isSelected = true
        holder.actualNote.text = currentNote.notes
        holder.date.text = currentNote.date
        holder.date.isSelected = true

        holder.noteContainerLayout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                generateRandomColor(),
                null
            )
        )
        holder.noteContainerLayout.setOnClickListener {
            iNoteItemClickListener.onItemCLicked(notesList[holder.adapterPosition])
        }

        holder.noteContainerLayout.setOnLongClickListener {
            iNoteItemClickListener.onLongItemCLicked(
                notesList[holder.adapterPosition],
                holder.noteContainerLayout
            )
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<NotesData>) {
        fullList.clear()
        fullList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(searchText: String) {
        notesList.clear()
        for (item in fullList) {
            if (item.title?.lowercase()?.contains(searchText.lowercase()) == true
                || item.notes?.lowercase()?.contains(searchText.lowercase()) == true) {
                notesList.add(item)
            }
        }
        notifyDataSetChanged()


    }


    private fun generateRandomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.note_background_color_1)
        list.add(R.color.note_background_color_2)
        list.add(R.color.note_background_color_3)
        list.add(R.color.note_background_color_4)
        list.add(R.color.note_background_color_5)
        list.add(R.color.note_background_color_6)
        list.add(R.color.note_background_color_7)
        list.add(R.color.note_background_color_8)
        list.add(R.color.note_background_color_9)
        list.add(R.color.note_background_color_10)
        list.add(R.color.note_background_color_11)
        list.add(R.color.note_background_color_12)
        list.add(R.color.note_background_color_13)
        list.add(R.color.note_background_color_14)
        list.add(R.color.note_background_color_15)
        list.add(R.color.note_background_color_16)
        list.add(R.color.note_background_color_17)
        list.add(R.color.note_background_color_18)
        list.add(R.color.note_background_color_19)
        list.add(R.color.note_background_color_20)

        val current = System.currentTimeMillis().toInt()
        val random = Random(current).nextInt(list.size)
        return list[random]

    }


    interface INoteItemClickListener {

        fun onItemCLicked(note: NotesData)
        fun onLongItemCLicked(note: NotesData, cardView: CardView)

    }

}
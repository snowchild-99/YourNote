package com.snowchild.yourNote.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.snowchild.yourNote.database.NoteDatabase
import com.snowchild.yourNote.database.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val notesRepository : NotesRepository

     val allNotes : LiveData<List<NotesData>>
    init {
        val dao = NoteDatabase.getDatabaseInstance(application)
            .getNoteDao()
        notesRepository = NotesRepository(dao)
        allNotes = notesRepository.allNotesData
    }

    fun insertNote(notesData: NotesData) = viewModelScope.launch(Dispatchers.IO) {

        notesRepository.insertNotes(notesData)
    }

    fun deleteNote(notesData: NotesData) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.deleteNotes(notesData)
    }

    fun updateNotes(notesData: NotesData) = viewModelScope.launch(Dispatchers.IO) {

        notesRepository.updateNotes(notesData)
    }


}
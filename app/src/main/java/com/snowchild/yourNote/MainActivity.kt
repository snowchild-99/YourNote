package com.snowchild.yourNote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.snowchild.yourNote.adapter.NotesAdapter
import com.snowchild.yourNote.database.NoteDatabase
import com.snowchild.yourNote.databinding.ActivityMainBinding
import com.snowchild.yourNote.model.NotesData
import com.snowchild.yourNote.model.NotesViewModel

class MainActivity : AppCompatActivity(), NotesAdapter.INoteItemClickListener,
    PopupMenu.OnMenuItemClickListener {

    private lateinit var binding : ActivityMainBinding
    private  lateinit var  database: NoteDatabase
    private lateinit var notesViewModel : NotesViewModel
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var selectedNote : NotesData

    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val note = result.data?.getSerializableExtra("note") as NotesData
            if(note !=null) {
                notesViewModel.updateNotes(note)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        notesViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NotesViewModel::class.java]

        notesViewModel.allNotes.observe(this) { list ->
            list?.let {
                notesAdapter.updateList(list)
            }
        }

        database = NoteDatabase.getDatabaseInstance(this)
    }

    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        notesAdapter = NotesAdapter(this,this)
        binding.recyclerView.adapter= notesAdapter

        val getNotesContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
            if(result.resultCode == Activity.RESULT_OK) {
                val note = result.data?.getSerializableExtra("note") as NotesData
                notesViewModel.insertNote(note)

            }
        }

        binding.addNote.setOnClickListener {
            val addNoteIntent  = Intent(this,AddNote::class.java)
            getNotesContent.launch(addNoteIntent)

        }

        binding.searchNote.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    notesAdapter.filterList(newText)
                    return true
                }
                return false
            }
        })

    }

    override fun onItemCLicked(note: NotesData) {
        val addNoteIntent  = Intent(this@MainActivity,AddNote::class.java)
        addNoteIntent.putExtra("current_note",note)
        updateNote.launch(addNoteIntent)
    }

    override fun onLongItemCLicked(note: NotesData, cardView: CardView) {

        selectedNote = note
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {
        val popupMenu = PopupMenu(this,cardView)
        popupMenu.setOnMenuItemClickListener(this@MainActivity)
        popupMenu.inflate(R.menu.pop_up_menu)
        popupMenu.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete) {
            notesViewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }
}
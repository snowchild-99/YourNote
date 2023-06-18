package com.snowchild.yourNote

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.snowchild.yourNote.databinding.ActivityAddNoteBinding
import com.snowchild.yourNote.databinding.ActivityMainBinding
import com.snowchild.yourNote.model.NotesData
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var notesData: NotesData
    private lateinit var oldNote: NotesData
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldNote = intent.getSerializableExtra("current_note") as NotesData
            binding.editNoteTitle.setText(oldNote.title)
            binding.editNoteBody.setText(oldNote.notes)
            isUpdate = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.checkBtn.setOnClickListener {
            val title = binding.editNoteTitle.text.toString()
            val noteBody = binding.editNoteBody.text.toString()
            if(title.isNotEmpty() || noteBody.isNotEmpty()) {
                val currentDate = SimpleDateFormat("EEE , d MMM yyyy HH:mm a")
                if (isUpdate) {
                    notesData = NotesData(oldNote.id,title,noteBody,currentDate.format(Date()))
                }
                else {
                    notesData = NotesData(null,title,noteBody,currentDate.format(Date()))

                }
                val intent = Intent()
                intent.putExtra("note",notesData)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
            else {
                Toast.makeText(this, "Enter Some Data", Toast.LENGTH_SHORT).show()
            }


        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }
}
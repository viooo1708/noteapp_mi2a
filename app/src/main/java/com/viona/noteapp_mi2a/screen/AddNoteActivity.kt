package com.viona.noteapp_mi2a.screen

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.viona.noteapp_mi2a.helper.NoteDatabaseHelper
import com.viona.noteapp_mi2a.databinding.ActivityAddNoteBinding
import com.viona.noteapp_mi2a.model.Note

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            Log.i("button","Anda klik ini")
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val note = Note(0, title, content)
            db.insertNote(note)
            finish()
            Toast.makeText(this, "Catatan disimpan", Toast.LENGTH_SHORT).show()
        }
    }
}
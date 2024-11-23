package com.viona.noteapp_mi2a.screen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.viona.noteapp_mi2a.R
import com.viona.noteapp_mi2a.databinding.ActivityUpdateNoteBinding
import com.viona.noteapp_mi2a.helper.NoteDatabaseHelper
import com.viona.noteapp_mi2a.model.Note

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateNoteBinding
    private lateinit var db : NoteDatabaseHelper

    private var noteId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)
        noteId = intent.getIntExtra("note_id", -1)
        if(noteId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.etEditJudul.setText(note.title)
        binding.etEditCatatan.setText(note.content)

        //proses update ke database
        binding.btnUpdateNote.setOnClickListener(){
            val newTitle = binding.etEditJudul.text.toString()//setelah apa yang didapatkan
            val newContent = binding.etEditCatatan.text.toString()//setelah apa yang didapatkan

            val updatedNote = Note(noteId, newTitle, newContent)
            db.updateNote(updatedNote)
            finish()
            Toast.makeText(this, "Update Berhasil", Toast.LENGTH_SHORT).show()
        }


        }
}
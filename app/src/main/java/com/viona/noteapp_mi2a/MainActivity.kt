package com.viona.noteapp_mi2a

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.viona.noteapp_mi2a.adapter.NotesAdapter
import com.viona.noteapp_mi2a.databinding.ActivityMainBinding
import com.viona.noteapp_mi2a.helper.NoteDatabaseHelper
import com.viona.noteapp_mi2a.screen.AddNoteActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var db : NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        notesAdapter = NotesAdapter(db.getAllNotes(), {notesAdapter ->
            // Navigasi ke Detail Activity

        },this)

        binding.notesRecycleview.layoutManager = LinearLayoutManager(this)
        binding.notesRecycleview.adapter = notesAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val notes = db.getAllNotes()
        notesAdapter.refreshData(notes)
    }
}
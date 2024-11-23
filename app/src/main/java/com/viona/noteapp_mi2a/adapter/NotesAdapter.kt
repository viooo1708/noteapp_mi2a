package com.viona.noteapp_mi2a.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.viona.noteapp_mi2a.DetailPageActivity
import com.viona.noteapp_mi2a.R
import com.viona.noteapp_mi2a.helper.NoteDatabaseHelper
import com.viona.noteapp_mi2a.model.Note
import com.viona.noteapp_mi2a.screen.UpdateNoteActivity

class NotesAdapter(
    private var notes: List<Note>,
    private val onClick: (Note) -> Unit,
    context: Context
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db : NoteDatabaseHelper = NoteDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItemJudul: TextView = itemView.findViewById(R.id.txtItemJudul)
        val txtItemIsiNote: TextView = itemView.findViewById(R.id.txtItemIsiNote)
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_note,
            parent,
            false
        )
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val noteData = notes[position]
        holder.txtItemJudul.text = noteData.title
        holder.txtItemIsiNote.text = noteData.content

        //Update
        holder.btnEdit.setOnClickListener(){
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", noteData.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener(){
            AlertDialog.Builder(holder.itemView.context).apply{
                setTitle("Confirmation")
                setMessage("Do you want to continue ?")
                setIcon(R.drawable.baseline_delete_24)

                setPositiveButton("yes") {
                        dialogInterface, i->
                    db.deletenote(noteData.id)
                    refreshData(db.getAllNotes())
                    Toast.makeText(holder.itemView.context, "note berhasil di hapus",
                        Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }
                setNeutralButton("no"){
                        dialogInterface, i->
                    dialogInterface.dismiss()
                }
            }.show()
        }

        // Set listener untuk menangani klik pada item
        holder.itemView.setOnClickListener {
            onClick(noteData)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }


//fitur untuk auto refresh data
    fun refreshData(newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }
}
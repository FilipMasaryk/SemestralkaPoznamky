package sk.uniza.semestralkapoznamky.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sk.uniza.semestralkapoznamky.data.Note
import sk.uniza.semestralkapoznamky.databinding.RecyclerviewNoteItemBinding

class NoteListAdapter : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemBinding = RecyclerviewNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name)
    }

    class NoteViewHolder(private val itemBinding: RecyclerviewNoteItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(text: String?) {
            itemBinding.textView.text = text
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.name == newItem.name && oldItem.description == newItem.description
        }
    }
}
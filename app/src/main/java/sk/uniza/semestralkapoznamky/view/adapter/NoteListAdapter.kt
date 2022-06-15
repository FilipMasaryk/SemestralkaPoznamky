package sk.uniza.semestralkapoznamky.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sk.uniza.semestralkapoznamky.data.Note
import sk.uniza.semestralkapoznamky.databinding.RecyclerviewNoteItemBinding
import sk.uniza.semestralkapoznamky.view.fragment.ListOfNotesFragmentDirections

class NoteListAdapter : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(WordsComparator()) {

    /**
     * Vytvara novy view poznamky
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemBinding =
            RecyclerviewNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(itemBinding)
    }

    /**
     * Nastavuje existujucemu view poznamky dane meno, popis, id a pin
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name, current.description, current.id, current.pinned)
    }

    /**
     * Pomocna trieda, ktora pracuje s view poznamky a nastavuje jej data
     *
     * @property itemBinding
     */
    class NoteViewHolder(private val itemBinding: RecyclerviewNoteItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(text: String, desc: String, id: Int, pinned: Boolean) {
            itemBinding.noteWrapper.setOnClickListener {
                val action =
                    ListOfNotesFragmentDirections.actionListOfNotesFragmentToNoteEditorFragment(id)
                it.findNavController().navigate(action)
            }
            itemBinding.nameView.text = text
            itemBinding.descView.text = desc
            if (pinned) {
                itemBinding.pinnedView.visibility = View.VISIBLE
            } else {
                itemBinding.pinnedView.visibility = View.INVISIBLE
            }
        }
    }

    /**
     * Trieda, ktora zistuje rovnost dat a tym sa predislo zbytocnemu prekreslovaniu rovnakych dat
     *
     */
    class WordsComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.name == newItem.name && oldItem.description == newItem.description && oldItem.pinned == newItem.pinned
        }
    }
}
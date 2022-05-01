package sk.uniza.semestralkapoznamky.view.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sk.uniza.semestralkapoznamky.R
import sk.uniza.semestralkapoznamky.data.Note
import sk.uniza.semestralkapoznamky.databinding.FragmentNoteEditorBinding
import sk.uniza.semestralkapoznamky.viewmodel.NoteEditorModel
import sk.uniza.semestralkapoznamky.viewmodel.NoteViewModel
@AndroidEntryPoint
class NoteEditorFragment : Fragment() {

    private lateinit var binding: FragmentNoteEditorBinding
    private val noteEditorModel: NoteEditorModel by viewModels()
    private var pinChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNoteEditorBinding.inflate(inflater)
        binding.fabAdd.setOnClickListener {
            val name = binding.nazovPoznamky.text.toString()
            val description = binding.popisPoznamky.text.toString()
            noteEditorModel.insert(Note(name,description))
            it.findNavController().navigate(R.id.action_noteEditorFragment_to_note_to_note_listOfNotesFragment)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.note_editor_menu, menu)
        togglePinButton(menu.findItem(R.id.action_pin))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_pin->togglePinButton(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun togglePinButton(item: MenuItem){
        if(!pinChecked) {
            item.setIcon(R.drawable.ic_push_pin_black_24dp)
        } else {
            item.setIcon(R.drawable.ic_baseline_push_pin_24)
        }
        pinChecked = !pinChecked
    }
}
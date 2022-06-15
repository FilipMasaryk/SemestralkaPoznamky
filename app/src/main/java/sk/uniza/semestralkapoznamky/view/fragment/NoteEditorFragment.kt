package sk.uniza.semestralkapoznamky.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import sk.uniza.semestralkapoznamky.R
import sk.uniza.semestralkapoznamky.data.Note
import sk.uniza.semestralkapoznamky.databinding.FragmentNoteEditorBinding
import sk.uniza.semestralkapoznamky.viewmodel.NoteEditorModel

@AndroidEntryPoint
class NoteEditorFragment : Fragment() {
    private lateinit var binding: FragmentNoteEditorBinding
    private val noteEditorModel: NoteEditorModel by viewModels()
    private val args: NoteEditorFragmentArgs by navArgs()

    /**
     * Povoli horne menu
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     * Inflate fragment_note_editor.xml a po kliknuti na floating action button fabAdd
     * nastavi do premennych name a description meno a popis poznamky.
     * Pokial sa ID poznamky rovna -1 (co znamena ze tu poznamku pridavame) tak insertne ju do databazy
     * Pokial sa ID poznamky nerovna -1 (co znamena ze danu poznamku editujeme) tak sa updatne v databaze
     * a po vykonani jednej z moznosti sa navigacia vrati na hlavny fragment ListofNotesFragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteEditorBinding.inflate(inflater)
        binding.fabAdd.setOnClickListener {
            val name = binding.nazovPoznamky.text.toString()
            val description = binding.popisPoznamky.text.toString()
            if (args.noteId == -1) {
                noteEditorModel.insert(
                    Note(
                        name,
                        description,
                        noteEditorModel.pinChecked.value ?: false
                    )
                )
            } else {
                noteEditorModel.update(
                    Note(
                        name,
                        description,
                        noteEditorModel.pinChecked.value ?: false,
                        args.noteId
                    )
                )
            }
            it.findNavController()
                .navigate(R.id.action_noteEditorFragment_to_note_to_note_listOfNotesFragment)
        }
        return binding.root
    }

    /**
     * Pokial vytvarame novu poznamku tak inflater inflatne note_add_menu.xml menu,
     * ktore neobsahuje moznost vymazania poznamky
     * Pokial editujeme existujucu poznamku tak inflater inflatne note_editor_menu.xml menu,
     * ktore obsahuje moznost aj vymazania existujucej poznamky
     * Taktiez pokial je pinButton zakliknuty tak zobrazi ikonku vyplneneho spendlika,
     * a pokial nie je zakliknuty tak zobrazi ikonku nevyplneneho spendlika
     * @param menu
     * @param inflater
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (args.noteId == -1) {
            inflater.inflate(R.menu.note_add_menu, menu)
        } else {
            inflater.inflate(R.menu.note_editor_menu, menu)
        }
        noteEditorModel.pinChecked.observe(viewLifecycleOwner) {
            val pinButton = menu.findItem(R.id.action_pin)
            if (!it) {
                pinButton.setIcon(R.drawable.ic_push_pin_black_24dp)
            } else {
                pinButton.setIcon(R.drawable.ic_baseline_push_pin_24)
            }
        }
    }

    /**
     * Pokial ID poznamky sa nerovna -1 (co znamena ze editujeme existujucu poznamku) tak
     * vyhlada poznamku na ktoru sme klikli a v editor fragmente nahra do policok
     * meno, popis a pin zakliknutej poznamky
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.noteId != -1 && savedInstanceState == null) {
            noteEditorModel.getSelectedNote(args.noteId).observe(viewLifecycleOwner) {
                binding.nazovPoznamky.setText(it.name)
                binding.popisPoznamky.setText(it.description)
                noteEditorModel.pinChecked.value = it.pinned
            }
        }

    }

    /**
     * Nastavi funkcionalitu menuItemu pin(na pripnutie poznamky) a smetiaku(na vymazanie poznamky)
     * @param item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_pin -> togglePinButton()
            R.id.action_delete -> deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Po zakliknuti menuItemu pin nastavi vo viewmodeli atribut pinChecked na opacnu hodnotu
     *
     */
    private fun togglePinButton() {
        noteEditorModel.pinChecked.value?.let { noteEditorModel.pinChecked.value = !it }
    }

    /**
     * Zobrazi okno pri mazani poznamky ktore sa opyta "Naozaj chcete vymazat poznamku?"
     * Po zakliknuti "Áno" sa poznámka vymaze a vrati sa v BackStacku
     * Po zakliknuti "Nie" okno zmizne
     *
     */
    private fun deleteNote() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.vymazanie_poznamky))
            .setNegativeButton("Áno") { _, _ ->
                noteEditorModel.delete(args.noteId)
                findNavController().popBackStack()
            }
            .setPositiveButton("Nie") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

}
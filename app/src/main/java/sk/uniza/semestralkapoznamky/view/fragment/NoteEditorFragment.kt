package sk.uniza.semestralkapoznamky.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sk.uniza.semestralkapoznamky.databinding.FragmentNoteEditorBinding

class NoteEditorFragment : Fragment() {

    private lateinit var binding: FragmentNoteEditorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNoteEditorBinding.inflate(inflater)
        return binding.root
    }

}
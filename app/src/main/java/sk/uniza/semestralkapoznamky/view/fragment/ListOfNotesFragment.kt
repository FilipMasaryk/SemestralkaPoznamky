package sk.uniza.semestralkapoznamky.view.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import sk.uniza.semestralkapoznamky.R
import sk.uniza.semestralkapoznamky.databinding.FragmentListOfNotesBinding
import sk.uniza.semestralkapoznamky.view.MainActivity
import sk.uniza.semestralkapoznamky.view.adapter.NoteListAdapter
import sk.uniza.semestralkapoznamky.viewmodel.NoteViewModel

@AndroidEntryPoint
class ListOfNotesFragment : Fragment() {

    private lateinit var binding: FragmentListOfNotesBinding
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListOfNotesBinding.inflate(inflater)
        binding.fabAdd.setOnClickListener {
            it.findNavController().navigate(R.id.action_listOfNotesFragment_to_noteEditorFragment)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu.findItem(R.id.action_search).actionView as SearchView
        searchItem.queryHint = getString(R.string.search)
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String): Boolean {
                return true
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = NoteListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel.allNotes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            showNoNotesImage(it.isEmpty())
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title = getString(R.string.notes)
    }

    private fun showNoNotesImage(isEmpty: Boolean) {
        if (isEmpty) {
            binding.noNotesWrapper.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.INVISIBLE
        } else {
            binding.noNotesWrapper.visibility = View.INVISIBLE
            binding.recyclerView.visibility = View.VISIBLE
        }

    }


}
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
    private lateinit var searchBar: SearchView

    /**
     * Povoli horne menu
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) //povoli horne menu
    }

    /**
     * Inflatne fragment_list_of_notes.xml a po kliknuti na floating action button
     * sa zobrazi fragment na pridanie novej poznamky
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListOfNotesBinding.inflate(inflater)
        binding.fabAdd.setOnClickListener {
            val action =
                ListOfNotesFragmentDirections.actionListOfNotesFragmentToNoteEditorFragment()
            it.findNavController().navigate(action)
        }
        return binding.root
    }

    /**
     * Inflatne horne menu a nastavi funkcnost search baru
     *
     * @param menu
     * @param inflater
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        searchBar = menuItem.actionView as SearchView
        searchBar.queryHint = getString(R.string.search)
        if (noteViewModel.searchText.isNotBlank()) {
            menuItem.expandActionView()
            searchBar.setQuery(noteViewModel.searchText, true)
        }
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    noteViewModel.searchText = query
                    noteViewModel.setSearchFilter(query)
                }
                return true
            }
        })
    }

    /**
     * Zobrazi poznamky a pokial je list poznamok prazdny tak zobrazi obrazok s textom
     * "Zatial nemate ziadne poznamky"
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = NoteListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel.allNotesFiltered.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            showNoNotesImage(it.isEmpty())
        }
    }

    /**
     * Nastavi hlavny titulok na "Poznámky"
     *
     */
    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title = getString(R.string.notes)
    }

    /**
     * Pokial je parameter isEmpty true tak nastavi recyclerView na invisible a noNotesWrapper na visible
     * Pokial nie je parameter isEmpty true tak nastavi recyclerView na visible a noNotesWrapper na invisible
     *
     * @param isEmpty
     */
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
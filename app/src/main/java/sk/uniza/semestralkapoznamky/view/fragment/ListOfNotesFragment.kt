package sk.uniza.semestralkapoznamky.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import sk.uniza.semestralkapoznamky.R
import sk.uniza.semestralkapoznamky.databinding.FragmentListOfNotesBinding
import sk.uniza.semestralkapoznamky.view.MainActivity

class ListOfNotesFragment : Fragment() {

    private lateinit var binding: FragmentListOfNotesBinding

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
        binding.fabAdd.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_listOfNotesFragment_to_noteEditorFragment)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu.findItem(R.id.action_search).actionView as SearchView
        searchItem.queryHint = getString(R.string.search)
        searchItem.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String): Boolean {
                return true
            }

        })
    }
    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title = getString(R.string.notes)
    }



}
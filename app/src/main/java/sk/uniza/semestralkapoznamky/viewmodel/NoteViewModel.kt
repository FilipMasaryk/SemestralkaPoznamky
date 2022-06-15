package sk.uniza.semestralkapoznamky.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import sk.uniza.semestralkapoznamky.data.Note
import sk.uniza.semestralkapoznamky.data.NoteRepository
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    var allNotesFiltered: LiveData<List<Note>>
    private val filter = MutableLiveData("%")
    var searchText: String = ""

    init {
        allNotesFiltered = Transformations.switchMap(filter) { filter ->
            repository.searchDatabase(filter).asLiveData()
        }
    }

    /**
     * Metoda ktora nastavi filter vyhldavania
     * Ak je vstupny string prazdny tak sa vyhladava bez filtru
     * Ak je zadany string tak sa vyhladava podla zadaneho stringu
     *
     * @param newFilter
     */
    fun setSearchFilter(newFilter: String) {
        val f = when {
            newFilter.isEmpty() -> "%"
            else -> "%$newFilter%"
        }
        filter.postValue(f)
    }
}
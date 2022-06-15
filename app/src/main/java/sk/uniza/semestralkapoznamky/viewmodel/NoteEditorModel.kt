package sk.uniza.semestralkapoznamky.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sk.uniza.semestralkapoznamky.data.Note
import sk.uniza.semestralkapoznamky.data.NoteRepository
import javax.inject.Inject

@HiltViewModel
class NoteEditorModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    val pinChecked = MutableLiveData(false)

    /**
     * Metoda na pridanie poznamky
     *
     * @param note
     * @return
     */
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    /**
     * Metoda na updatnutie poznamky
     *
     * @param note
     * @return
     */
    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    /**
     * Metoda na vymazanie poznamky podla zadaneho ID
     *
     * @param id
     * @return
     */
    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    /**
     * Metoda na vratenie poznamky podla zadaneho ID
     *
     * @param id
     * @return
     */
    fun getSelectedNote(id: Int): LiveData<Note> {
        return repository.getSelectedNote(id).asLiveData()
    }

}
package sk.uniza.semestralkapoznamky.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sk.uniza.semestralkapoznamky.data.Note
import sk.uniza.semestralkapoznamky.data.NoteRepository
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    val allNotes: LiveData<List<Note>> = repository.allNotes.asLiveData()
}
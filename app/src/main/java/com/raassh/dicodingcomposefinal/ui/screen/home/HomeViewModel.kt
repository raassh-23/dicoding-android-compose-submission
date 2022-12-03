package com.raassh.dicodingcomposefinal.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raassh.dicodingcomposefinal.data.AnimeRepository
import com.raassh.dicodingcomposefinal.data.model.Anime
import com.raassh.dicodingcomposefinal.data.model.WatchStatus
import com.raassh.dicodingcomposefinal.ui.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: AnimeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Pair<Anime, WatchStatus>>>> =
        MutableStateFlow(UiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    fun getAllAnime() {
        viewModelScope.launch {
            repository.getAllAnime()
                .catch { e ->
                    _uiState.value = UiState.Error(e.message.toString())
                }
                .collect { animeList ->
                    _uiState.value = UiState.Success(animeList)
                }
        }
    }
}
package com.raassh.dicodingcomposefinal.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _watchStatus = mutableStateOf(WatchStatus.UNTRACKED)
    val watchStatus: State<WatchStatus> get() = _watchStatus

    fun setQuery(newQuery: String) {
        _query.value = newQuery
        searchAnime()
    }

    fun setWatchStatus(status: WatchStatus) {
        _watchStatus.value = status
        searchAnime()
    }

    fun searchAnime() {
        viewModelScope.launch {
            repository.searchAnime(_query.value, _watchStatus.value)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message.toString())
                }
                .collect { animeList ->
                    _uiState.value = UiState.Success(animeList)
                }
        }
    }
}
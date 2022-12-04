package com.raassh.dicodingcomposefinal.ui.screen.detail

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

class DetailViewModel(private val repository: AnimeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Pair<Anime, WatchStatus>>> =
        MutableStateFlow(UiState.Loading)
    val uiState get() = _uiState.asStateFlow()

//    private val _watchStatus: MutableStateFlow<WatchStatus> =
//        MutableStateFlow(WatchStatus.UNTRACKED)
//    val watchStatus get() = _watchStatus.asStateFlow()

    fun getAnimeById(animeId: Long) {
        viewModelScope.launch {
            repository.getAnimeById(animeId)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message.toString())
                }
                .collect { data ->
//                    _watchStatus.value = data.second
                    _uiState.value = UiState.Success(data)
                }
        }
    }

    fun setWatchStatus(animeId: Long, status: WatchStatus) {
        viewModelScope.launch {
            repository.updateAnimeStatus(animeId, status)
            getAnimeById(animeId)
        }
    }
}

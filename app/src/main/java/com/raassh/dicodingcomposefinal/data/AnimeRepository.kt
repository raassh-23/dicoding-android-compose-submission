package com.raassh.dicodingcomposefinal.data

import com.raassh.dicodingcomposefinal.data.model.WatchStatus
import com.raassh.dicodingcomposefinal.data.model.fakeAnimeList
import kotlinx.coroutines.flow.flowOf

class AnimeRepository {
    private val animeList = fakeAnimeList.map {
        it to WatchStatus.UNTRACKED
    }.toMutableList()

    fun getAnimeById(id: Long) = flowOf(animeList.first { (anime, _) ->
        anime.id == id
    })

    fun searchAnime(query: String, filterStatus: WatchStatus? = null) = flowOf(
        animeList.filter { (anime, status) ->
            (anime.title.contains(
                query,
                ignoreCase = true
            ) || anime.translatedTitle?.contains(
                query,
                ignoreCase = true
            ) == true) && (filterStatus == null || status == filterStatus)
        }
    )

    fun updateAnimeStatus(id: Long, newStatus: WatchStatus) {
        val index = animeList.indexOfFirst { (anime, _) ->
            anime.id == id
        }

        if (index < 0) {
            return
        }

        animeList[index] = animeList[index].copy(second = newStatus)
    }

    // for testing purpose
    fun resetAnimeList() {
        animeList.clear()
        animeList.addAll(fakeAnimeList.map {
            it to WatchStatus.UNTRACKED
        })
    }

    companion object {
        @Volatile
        private var instance: AnimeRepository? = null

        fun getInstance(): AnimeRepository =
            instance ?: synchronized(this) {
                AnimeRepository().apply {
                    instance = this
                }
            }
    }
}
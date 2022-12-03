package com.raassh.dicodingcomposefinal.data

import com.raassh.dicodingcomposefinal.data.model.WatchStatus
import com.raassh.dicodingcomposefinal.data.model.fakeAnimeList
import kotlinx.coroutines.flow.flowOf

class AnimeRepository {
    private val animeList = fakeAnimeList.map {
        it to WatchStatus.UNTRACKED
    }.toMutableList()

    fun getAllAnime() = flowOf(animeList)

    fun getAnimeById(id: Long) = animeList.first { (anime, _) ->
        anime.id == id
    }

    fun getAnimeByStatus(filterStatus: WatchStatus) = animeList.filter { (_, status) ->
        status == filterStatus
    }

    fun updateAnimeStatus(id: Long, newStatus: WatchStatus) {
        val index = animeList.indexOfFirst { (anime, _) ->
            anime.id == id
        }

        if (index < 0) {
            return
        }

        animeList[index] = animeList[index].copy(second = newStatus)
    }
}
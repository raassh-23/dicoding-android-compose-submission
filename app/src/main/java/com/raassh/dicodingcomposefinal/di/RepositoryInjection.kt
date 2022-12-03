package com.raassh.dicodingcomposefinal.di

import com.raassh.dicodingcomposefinal.data.AnimeRepository

object RepositoryInjection {
    fun provideAnimeRepository() = AnimeRepository.getInstance()
}
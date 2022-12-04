package com.raassh.dicodingcomposefinal.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raassh.dicodingcomposefinal.data.AnimeRepository
import com.raassh.dicodingcomposefinal.ui.screen.detail.DetailViewModel
import com.raassh.dicodingcomposefinal.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: AnimeRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
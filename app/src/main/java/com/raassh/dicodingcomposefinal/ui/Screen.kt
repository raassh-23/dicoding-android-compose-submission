package com.raassh.dicodingcomposefinal.ui

import com.raassh.dicodingcomposefinal.R

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
    object Detail : Screen("home/{animeId}") {
        fun createRoute(animeId: Long) = "home/$animeId"
    }

    companion object {
        fun titleFromRoute(route: String): Int {
            return when (route) {
                About.route -> R.string.about
                Detail.route -> R.string.detail
                else -> R.string.app_name
            }
        }
    }
}

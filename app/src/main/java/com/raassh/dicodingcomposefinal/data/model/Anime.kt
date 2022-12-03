package com.raassh.dicodingcomposefinal.data.model

data class Anime(
    val id: Long,
    val title: String,
    val translatedTitle: String? = null,
    val synopsis: String,
    val coverImageUrl: String,
    val startDate: String,
    val endDate: String? = null,
    val rating: Double,
    val totalEpisodes: Int?,
)

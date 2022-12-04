package com.raassh.dicodingcomposefinal.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raassh.dicodingcomposefinal.data.model.Anime
import com.raassh.dicodingcomposefinal.data.model.WatchStatus
import com.raassh.dicodingcomposefinal.data.model.fakeAnimeList
import com.raassh.dicodingcomposefinal.ui.theme.DicodingComposeFinalTheme

@Composable
fun AnimeList(
    animeList: List<Pair<Anime, WatchStatus>>,
    onAnimeClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(animeList, key = { it.first.id }) { (anime, _) ->
            AnimeListItem(
                title = anime.title,
                coverImageUrl = anime.coverImageUrl,
                rating = anime.rating,
                totalEpisodes = anime.totalEpisodes,
                modifier = Modifier
                    .aspectRatio(0.7f)
                    .clickable {
                        onAnimeClick(anime.id)
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeListPreview() {
    DicodingComposeFinalTheme {
        AnimeList(animeList = fakeAnimeList.map {
            it to WatchStatus.UNTRACKED
        }, onAnimeClick = {})
    }
}
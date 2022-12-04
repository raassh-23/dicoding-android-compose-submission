package com.raassh.dicodingcomposefinal.ui.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raassh.dicodingcomposefinal.R
import com.raassh.dicodingcomposefinal.data.model.Anime
import com.raassh.dicodingcomposefinal.data.model.WatchStatus
import com.raassh.dicodingcomposefinal.data.model.fakeAnimeList
import com.raassh.dicodingcomposefinal.ui.theme.DicodingComposeFinalTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeList(
    animeList: List<Pair<Anime, WatchStatus>>,
    onAnimeClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (animeList.isEmpty()) {
        Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
            Text(text = stringResource(R.string.empty_anime_list), textAlign = TextAlign.Center)
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(top = 0.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
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
                        .animateItemPlacement(tween(100))
                        .clickable {
                            onAnimeClick(anime.id)
                        }
                )
            }
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

@Preview(showBackground = true)
@Composable
fun AnimeListEmptyPreview() {
    DicodingComposeFinalTheme {
        AnimeList(animeList = listOf(), onAnimeClick = {})
    }
}
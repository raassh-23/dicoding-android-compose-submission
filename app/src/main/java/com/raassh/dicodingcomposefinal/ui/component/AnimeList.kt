package com.raassh.dicodingcomposefinal.ui.component

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raassh.dicodingcomposefinal.R
import com.raassh.dicodingcomposefinal.data.model.Anime
import com.raassh.dicodingcomposefinal.data.model.WatchStatus
import com.raassh.dicodingcomposefinal.data.model.fakeAnimeList
import com.raassh.dicodingcomposefinal.ui.theme.DicodingComposeFinalTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeList(
    animeList: List<Pair<Anime, WatchStatus>>,
    onAnimeClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val showButton by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (animeList.isEmpty()) {
            Text(
                text = stringResource(R.string.empty_anime_list),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(
                    top = 0.dp,
                    bottom = 120.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.testTag("anime_list")
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

            AnimatedVisibility(
                visible = showButton,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .align(Alignment.BottomCenter)
            ) {
                ScrollToTopButton(onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                })
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
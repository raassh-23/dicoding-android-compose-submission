package com.raassh.dicodingcomposefinal.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raassh.dicodingcomposefinal.data.model.Anime
import com.raassh.dicodingcomposefinal.data.model.WatchStatus
import com.raassh.dicodingcomposefinal.di.RepositoryInjection
import com.raassh.dicodingcomposefinal.ui.component.AnimeListItem
import com.raassh.dicodingcomposefinal.ui.screen.home.HomeViewModel
import com.raassh.dicodingcomposefinal.ui.theme.DicodingComposeFinalTheme
import com.raassh.dicodingcomposefinal.ui.utils.UiState
import com.raassh.dicodingcomposefinal.ui.utils.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory =ViewModelFactory(RepositoryInjection.provideAnimeRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        when(it) {
            is UiState.Loading -> {
                viewModel.getAllAnime()
            }
            is UiState.Success -> {
                HomeContent(
                    animeList = it.data,
                    modifier = modifier
                )
            }
            is UiState.Error -> {
                // should handle error here
                // but since we use data dummy, we don't need to do anything
            }
        }
    }
}

@Composable
fun HomeContent(
    animeList: List<Pair<Anime, WatchStatus>>,
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
                modifier = Modifier.aspectRatio(0.7f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    DicodingComposeFinalTheme {
        HomeScreen()
    }
}
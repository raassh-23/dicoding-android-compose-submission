package com.raassh.dicodingcomposefinal.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raassh.dicodingcomposefinal.data.model.Anime
import com.raassh.dicodingcomposefinal.data.model.WatchStatus
import com.raassh.dicodingcomposefinal.di.RepositoryInjection
import com.raassh.dicodingcomposefinal.ui.component.AnimeList
import com.raassh.dicodingcomposefinal.ui.component.FilterList
import com.raassh.dicodingcomposefinal.ui.component.SearchBar
import com.raassh.dicodingcomposefinal.ui.theme.DicodingComposeFinalTheme
import com.raassh.dicodingcomposefinal.ui.utils.UiState
import com.raassh.dicodingcomposefinal.ui.utils.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(RepositoryInjection.provideAnimeRepository())
    ),
    navigateToDetail: (Long) -> Unit = {},
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.searchAnime()
            }
            is UiState.Success -> {
                HomeContent(
                    animeList = it.data,
                    query = viewModel.query.value,
                    onQueryChange = viewModel::setQuery,
                    watchStatus = viewModel.watchStatus.value,
                    onWatchStatusChange = viewModel::setWatchStatus,
                    navigateToDetail = navigateToDetail,
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
    modifier: Modifier = Modifier,
    watchStatus: WatchStatus? = null,
    onWatchStatusChange: (WatchStatus?) -> Unit = {},
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    navigateToDetail: (Long) -> Unit = {},
) {
    Column(modifier = modifier) {
        SearchBar(query = query, onQueryChange = onQueryChange)
        FilterList(selected = watchStatus, onFilterClick = onWatchStatusChange)
        Spacer(modifier = Modifier.height(16.dp))
        AnimeList(animeList = animeList, onAnimeClick = navigateToDetail)
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    DicodingComposeFinalTheme {
        HomeScreen()
    }
}
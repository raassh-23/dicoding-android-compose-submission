package com.raassh.dicodingcomposefinal.ui.screen.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.raassh.dicodingcomposefinal.R
import com.raassh.dicodingcomposefinal.data.model.WatchStatus
import com.raassh.dicodingcomposefinal.di.RepositoryInjection
import com.raassh.dicodingcomposefinal.ui.component.Dropdown
import com.raassh.dicodingcomposefinal.ui.component.TextWithIcon
import com.raassh.dicodingcomposefinal.ui.theme.DicodingComposeFinalTheme
import com.raassh.dicodingcomposefinal.ui.utils.UiState
import com.raassh.dicodingcomposefinal.ui.utils.ViewModelFactory
import com.raassh.dicodingcomposefinal.ui.utils.withDateFormat

@Composable
fun DetailScreen(
    animeId: Long,
    viewModel: DetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            RepositoryInjection.provideAnimeRepository()
        )
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getAnimeById(animeId)
            }
            is UiState.Success -> {
                val (anime, watchStatus) = it.data

                DetailContent(
                    title = anime.title,
                    translatedTitle = anime.translatedTitle,
                    synopsis = anime.synopsis,
                    coverImageUrl = anime.coverImageUrl,
                    startDate = anime.startDate,
                    endDate = anime.endDate,
                    rating = anime.rating,
                    totalEpisodes = anime.totalEpisodes,
                    watchStatus = watchStatus,
                    onSelectWatchStatus = { status ->
                        viewModel.setWatchStatus(animeId, status)
                    },
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
fun DetailContent(
    title: String,
    translatedTitle: String?,
    synopsis: String,
    coverImageUrl: String,
    startDate: String,
    endDate: String?,
    rating: Double,
    totalEpisodes: Int?,
    watchStatus: WatchStatus,
    modifier: Modifier = Modifier,
    onSelectWatchStatus: (WatchStatus) -> Unit = {},
) {


    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        if (translatedTitle != null) {
            Text(
                text = translatedTitle,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        AsyncImage(
            model = coverImageUrl,
            contentDescription = stringResource(id = R.string.cover_image_description, title),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(250.dp)
                .aspectRatio(0.7f)
                .border(
                    2.dp,
                    MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                    RoundedCornerShape(8.dp)
                )
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.watch_status),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Dropdown(selected = watchStatus, data = WatchStatus.values().toList(), label = {
            stringResource(
                id = it.resId
            )
        }, onSelect = onSelectWatchStatus, modifier = Modifier.padding(top = 4.dp))

        Spacer(modifier = Modifier.height(8.dp))

        val ratingDescription =
            stringResource(id = R.string.rating_content_description, rating)
        val totalEpisodesDescription = if (totalEpisodes != null) {
            stringResource(
                id = R.string.total_episodes_content_description,
                totalEpisodes
            )
        } else {
            stringResource(id = R.string.total_episodes_content_description_unknown)
        }
        val airedDateDescription = if (startDate != endDate) {
            stringResource(
                id = R.string.aired_tv_date_content_description,
                startDate.withDateFormat(),
                endDate?.withDateFormat() ?: stringResource(id = R.string.present)
            )
        } else {
            stringResource(
                id = R.string.aired_movie_date_content_description,
                startDate.withDateFormat()
            )
        }

        Text(
            text = stringResource(R.string.information),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        TextWithIcon(
            icon = Icons.Default.Star,
            iconTint = MaterialTheme.colors.onSurface,
            text = rating.toString(),
            textStyle = MaterialTheme.typography.h6,
            modifier = Modifier.semantics(mergeDescendants = true) {
                contentDescription = ratingDescription
            }
        )
        TextWithIcon(
            icon = Icons.Default.VideoLibrary,
            iconTint = MaterialTheme.colors.onSurface,
            text = stringResource(
                id = R.string.episodes,
                totalEpisodes?.toString() ?: stringResource(id = R.string.unknown)
            ),
            textStyle = MaterialTheme.typography.h6,
            modifier = Modifier
                .semantics(mergeDescendants = true) {
                    contentDescription = totalEpisodesDescription
                }
        )
        TextWithIcon(
            icon = Icons.Default.DateRange,
            iconTint = MaterialTheme.colors.onSurface,
            text = if (startDate != endDate) {
                stringResource(
                    id = R.string.aired_tv,
                    startDate.withDateFormat(),
                    endDate?.withDateFormat() ?: stringResource(id = R.string.present)
                )
            } else {
                stringResource(
                    id = R.string.aired_movie,
                    startDate.withDateFormat()
                )
            },
            textStyle = MaterialTheme.typography.h6,
            modifier = Modifier
                .semantics(mergeDescendants = true) {
                    contentDescription = airedDateDescription
                }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.synopsis),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = synopsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DicodingComposeFinalTheme {
//        DetailScreen(animeId = 1)
        DetailContent(
            title = "Shingeki no Kyojin",
            translatedTitle = "Attack on Titan",
            synopsis = "Centuries ago, mankind was slaughtered to near extinction by monstrous humanoid creatures called Titans, forcing humans to hide in fear behind enormous concentric walls. What makes these giants truly terrifying is that their taste for human flesh is not born out of hunger but what appears to be out of pleasure. To ensure their survival, the remnants of humanity began living within defensive barriers, resulting in one hundred years without a single titan encounter. However, that fragile calm is soon shattered when a colossal Titan manages to breach the supposedly impregnable outer wall, reigniting the fight for survival against the man-eating abominations.\n" +
                    "\n" +
                    "After witnessing a horrific personal loss at the hands of the invading creatures, Eren Yeager dedicates his life to their eradication by enlisting into the Survey Corps, an elite military unit that combats the merciless humanoids outside the protection of the walls. Eren, his adopted sister Mikasa Ackerman, and his childhood friend Armin Arlert join the brutal war against the Titans and race to discover a way of defeating them before the last walls are breached.",
            coverImageUrl = "https://cdn.myanimelist.net/images/anime/10/47347l.jpg",
            startDate = "Apr 7, 2013",
            endDate = "Sep 29, 2013",
            rating = 8.53,
            totalEpisodes = 25,
            watchStatus = WatchStatus.UNTRACKED,
        )
    }
}
package com.raassh.dicodingcomposefinal.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.raassh.dicodingcomposefinal.R
import com.raassh.dicodingcomposefinal.ui.theme.DicodingComposeFinalTheme

@Composable
fun AnimeListItem(
    title: String,
    coverImageUrl: String,
    rating: Double,
    totalEpisodes: Int?,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxSize()) {
        Box {
            AsyncImage(
                model = coverImageUrl,
                contentDescription = stringResource(id = R.string.cover_image_description, title),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.75f))
                    .padding(8.dp)
            ) {
                Text(
                    text = title, style = MaterialTheme.typography.h6.copy(
                        color = Color.White
                    ), maxLines = 2, overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                    TextWithIcon(
                        icon = Icons.Default.Star,
                        iconColorFilter = ColorFilter.tint(Color.White),
                        text = rating.toString(),
                        textStyle = MaterialTheme.typography.subtitle1.copy(color = Color.White),
                        modifier = Modifier.semantics(mergeDescendants = true) {
                            contentDescription = ratingDescription
                        }
                    )
                    TextWithIcon(
                        icon = Icons.Default.VideoLibrary,
                        iconColorFilter = ColorFilter.tint(Color.White),
                        text = totalEpisodes?.toString() ?: stringResource(id = R.string.unknown),
                        textStyle = MaterialTheme.typography.subtitle1.copy(color = Color.White),
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .semantics(mergeDescendants = true) {
                                contentDescription = totalEpisodesDescription
                            }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeListItemPreview() {
    DicodingComposeFinalTheme {
        AnimeListItem(
            title = "Shingeki no Kyojin",
            coverImageUrl = "https://cdn.myanimelist.net/images/anime/10/47347l.jpg",
            rating = 8.53,
            totalEpisodes = 25,
        )
    }
}
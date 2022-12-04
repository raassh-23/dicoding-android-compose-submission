package com.raassh.dicodingcomposefinal.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raassh.dicodingcomposefinal.R
import com.raassh.dicodingcomposefinal.ui.theme.DicodingComposeFinalTheme

@Composable
fun FilterListItem(
    filterName: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        backgroundColor = if (isSelected) {
            MaterialTheme.colors.primary
        } else {
            MaterialTheme.colors.surface
        },
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
        ),
        modifier = modifier
    ) {
        Text(text = filterName, modifier = Modifier.padding(4.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun FilterListItemSelectedPreview() {
    DicodingComposeFinalTheme {
        FilterListItem(stringResource(id = R.string.untracked), true)
    }
}

@Preview(showBackground = true)
@Composable
fun FilterListItemUnelectedPreview() {
    DicodingComposeFinalTheme {
        FilterListItem(stringResource(id = R.string.untracked), false)
    }
}
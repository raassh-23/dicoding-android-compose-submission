package com.raassh.dicodingcomposefinal.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raassh.dicodingcomposefinal.data.model.WatchStatus

@Composable
fun FilterList(
    selected: WatchStatus?,
    modifier: Modifier = Modifier,
    onFilterClick: (WatchStatus?) -> Unit = {},
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(WatchStatus.values(), key = { it.resId }) { filter ->
            FilterListItem(
                filterName = stringResource(id = filter.resId),
                isSelected = filter == selected,
                modifier = Modifier.clickable {
                    onFilterClick(if (filter == selected) null else filter)
                }
            )
        }
    }
}
package com.raassh.dicodingcomposefinal.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import com.raassh.dicodingcomposefinal.R
import com.raassh.dicodingcomposefinal.data.model.WatchStatus
import com.raassh.dicodingcomposefinal.ui.theme.DicodingComposeFinalTheme

@Composable
fun <T> Dropdown(
    selected: T,
    data: List<T>,
    label: @Composable (T) -> String,
    modifier: Modifier = Modifier,
    onSelect: (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    OutlinedTextField(
        value = label(selected),
        onValueChange = { },
        enabled = false,
        textStyle = MaterialTheme.typography.h6,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = MaterialTheme.colors.onSurface,
            disabledBorderColor = MaterialTheme.colors.onSurface,
            focusedBorderColor = MaterialTheme.colors.onSurface,
            unfocusedBorderColor = MaterialTheme.colors.onSurface,
        ),
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            }.clickable { expanded = !expanded },
        trailingIcon = {
            Icon(
                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = if (expanded)
                    stringResource(R.string.collapse)
                else
                    stringResource(R.string.expand)
            )
        }
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
    ) {
        data.forEach {
            DropdownMenuItem(onClick = {
                onSelect(it)
                expanded = false
            }) {
                Text(text = label(it))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownPreview() {
    DicodingComposeFinalTheme {
        Dropdown(
            selected = WatchStatus.WATCHING,
            data = WatchStatus.values().toList(),
            label = { stringResource(id = it.resId) },
            onSelect = {}
        )
    }
}
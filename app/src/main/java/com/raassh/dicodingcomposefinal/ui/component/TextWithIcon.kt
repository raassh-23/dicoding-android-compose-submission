package com.raassh.dicodingcomposefinal.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raassh.dicodingcomposefinal.ui.theme.DicodingComposeFinalTheme


@Composable
fun TextWithIcon(
    icon: ImageVector,
    iconColorFilter: ColorFilter,
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Image(imageVector = icon, contentDescription = null, colorFilter = iconColorFilter)
        Text(text = text, style = textStyle, modifier = Modifier.padding(start = 4.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TextWithIconPreview() {
    DicodingComposeFinalTheme {
        TextWithIcon(
            icon = Icons.Default.Star,
            iconColorFilter = ColorFilter.tint(Color.LightGray),
            text = "10.0",
            textStyle = MaterialTheme.typography.h6
        )
    }
}
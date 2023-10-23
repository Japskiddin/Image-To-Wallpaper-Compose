package io.github.japskiddin.imagetowallpaper.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.japskiddin.imagetowallpaper.R
import io.github.japskiddin.imagetowallpaper.ui.theme.ImageToWallpaperTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            ToolBar(title = stringResource(id = R.string.app_name))
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            )
            {
                Text("Android")
            }
        },
        modifier = modifier
    )
}

@Composable
fun ToolBar(title: String, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = stringResource(id = R.string.settings),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    ImageToWallpaperTheme {
        HomeScreen()
    }
}

@Preview
@Composable
fun ToolBarPreview(title: String = stringResource(id = R.string.app_name)) {
    ImageToWallpaperTheme {
        ToolBar(title)
    }
}
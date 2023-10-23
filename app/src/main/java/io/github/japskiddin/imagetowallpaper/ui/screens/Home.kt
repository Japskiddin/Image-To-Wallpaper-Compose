package io.github.japskiddin.imagetowallpaper.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.canhub.cropper.CropImageView
import io.github.japskiddin.imagetowallpaper.R
import io.github.japskiddin.imagetowallpaper.ui.theme.ImageToWallpaperTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            ToolBar(title = stringResource(id = R.string.app_name))
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .systemBarsPadding()
            )
            {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        CropImageView(context).apply {
                            guidelines = CropImageView.Guidelines.ON
                        }
                    })
                Row(
                    modifier = modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    HomeButton(
                        icon = R.drawable.ic_wallpaper,
                        desc = R.string.set_wallpaper,
                        onClick = {/*TODO*/ })
                    HomeButton(
                        icon = R.drawable.ic_gallery,
                        desc = R.string.select_image,
                        onClick = {/*TODO*/ })
                }
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

@Composable
fun HomeButton(icon: Int, desc: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = desc),
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview(name = "Light mode", showBackground = true, showSystemUi = true)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    showSystemUi = true
)
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

@Preview
@Composable
fun HomeButtonPreview() {
    ImageToWallpaperTheme {
        HomeButton(
            icon = R.drawable.ic_gallery,
            desc = R.string.select_image,
            onClick = {})
    }
}
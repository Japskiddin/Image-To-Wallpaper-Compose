package io.github.japskiddin.imagetowallpapercompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.WallpaperType
import io.github.japskiddin.imagetowallpapercompose.utils.PreviewWithTheme

@Composable
fun Menu(
    modifier: Modifier = Modifier,
    onSelectImageClick: () -> Unit,
    onSetWallpaper: (WallpaperType) -> Unit
) {
    var showPopup by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Box {
            MenuButton(
                icon = R.drawable.ic_wallpaper,
                desc = R.string.set_wallpaper,
                onClick = { showPopup = true }
            )
            PopupMenu(
                expanded = showPopup,
                onDismiss = { showPopup = false },
                onSetWallpaperHome = {
                    showPopup = false
                    onSetWallpaper(WallpaperType.HOME)
                },
                onSetWallpaperLock = {
                    showPopup = false
                    onSetWallpaper(WallpaperType.LOCK)
                },
                onSetWallpaperBoth = {
                    showPopup = false
                    onSetWallpaper(WallpaperType.BOTH)
                }
            )
        }
        MenuButton(
            icon = R.drawable.ic_gallery,
            desc = R.string.select_image,
            onClick = onSelectImageClick
        )
    }
}

@Composable
fun PopupMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onSetWallpaperHome: () -> Unit,
    onSetWallpaperLock: () -> Unit,
    onSetWallpaperBoth: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.as_home_screen),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_set_wallpaper_home),
                    contentDescription = stringResource(id = R.string.as_home_screen)
                )
            },
            onClick = onSetWallpaperHome
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.as_lock_screen),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_set_wallpaper_lock),
                    contentDescription = stringResource(id = R.string.as_lock_screen)
                )
            },
            onClick = onSetWallpaperLock
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.as_both),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_set_wallpaper_both),
                    contentDescription = stringResource(id = R.string.as_both)
                )
            },
            onClick = onSetWallpaperBoth
        )
    }
}

@Composable
fun MenuButton(
    icon: Int, desc: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onClick() },
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

@Preview
@Composable
fun MenuButtonPreview() {
    PreviewWithTheme {
        MenuButton(
            icon = R.drawable.ic_gallery,
            desc = R.string.select_image,
            onClick = {})
    }
}
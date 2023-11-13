package io.github.japskiddin.imagetowallpapercompose

import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.japskiddin.imagetowallpapercompose.ui.components.MenuButton
import io.github.japskiddin.imagetowallpapercompose.ui.components.OptionItem
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme
import io.github.japskiddin.imagetowallpapercompose.utils.PreviewWithTheme
import io.github.japskiddin.imagetowallpapercompose.utils.hasPermission
import io.github.japskiddin.imagetowallpapercompose.utils.openFile
import io.github.japskiddin.imagetowallpapercompose.utils.requestPermission
import io.moyuru.cropify.AspectRatio
import io.moyuru.cropify.Cropify
import io.moyuru.cropify.CropifyOption
import io.moyuru.cropify.CropifyState
import io.moyuru.cropify.rememberCropifyState

// https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-4-pathway-2%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-navigation#6
// https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#7

@Composable
fun ImageToWallpaperApp(
    modifier: Modifier = Modifier,
    cropViewModel: CropViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    ImageToWallpaperTheme {
        val backgroundColor = MaterialTheme.colorScheme.background

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor
        ) {
            val context = LocalContext.current
            val aspectRatioState by settingsViewModel.aspectRatioState.collectAsState()
            val cropifyState = rememberCropifyState()
            val cropifyOption = remember {
                mutableStateOf(
                    CropifyOption(
                        backgroundColor = backgroundColor,
                        frameAspectRatio = AspectRatio(
                            aspectRatioState.cropRatio.width,
                            aspectRatioState.cropRatio.height
                        ),
                        maskColor = backgroundColor
                    )
                )
            }

            val imageUri by cropViewModel.imageUriState.collectAsState()

            val openDocumentLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.OpenDocument(),
                onResult = { uri -> uri?.let { cropViewModel.setImageUri(it) } }
            )
            val getContentLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = { uri -> uri?.let { cropViewModel.setImageUri(it) } }
            )
            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    if (isGranted) {
                        openFile(context, openDocumentLauncher, getContentLauncher)
                    } else {
                        Toast.makeText(
                            context,
                            R.string.err_permission_not_granted,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )

            val onSelectImageClick = {
                if (hasPermission(context)) {
                    openFile(context, openDocumentLauncher, getContentLauncher)
                } else {
                    requestPermission(context, requestPermissionLauncher)
                }
            }

            ImageToWallpaperAppContent(
                imageUri = imageUri,
                cropifyState = cropifyState,
                cropifyOption = cropifyOption.value,
                modifier = modifier,
                onSelectImageClick = onSelectImageClick,
                onChangeAppTheme = { settingsViewModel.setAppTheme(it) }
            )
        }
    }
}

@Composable
fun ImageToWallpaperAppContent(
    modifier: Modifier = Modifier,
    imageUri: Uri? = null,
    cropifyState: CropifyState = CropifyState(),
    cropifyOption: CropifyOption = CropifyOption(),
    onSelectImageClick: () -> Unit = {},
    onChangeAppTheme: (AppTheme) -> Unit = {}
) {
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        BottomSheet(
            onDismiss = { showBottomSheet = false },
            onChangeAppTheme = onChangeAppTheme
        )
    }

    Scaffold(
        topBar = {
            ToolBar(
                onOptionsClick = { showBottomSheet = true },
                modifier = modifier
            )
        },
        content = {
            Column(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize()
            )
            {
                if (imageUri == null) {
                    Spacer(
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                } else {
                    Cropify(
                        uri = imageUri,
                        state = cropifyState,
                        option = cropifyOption,
                        onImageCropped = {},
                        onFailedToLoadImage = {
                            Toast.makeText(
                                context,
                                R.string.err_load_image,
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    MenuButton(
                        icon = R.drawable.ic_wallpaper,
                        desc = R.string.set_wallpaper,
                        onClick = {/*TODO*/ })
                    MenuButton(
                        icon = R.drawable.ic_gallery,
                        desc = R.string.select_image,
                        onClick = onSelectImageClick
                    )
                }
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onChangeAppTheme: (AppTheme) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Options(modifier = modifier, onChangeAppTheme = onChangeAppTheme)
    }
}

@Composable
fun Options(
    modifier: Modifier = Modifier,
    onChangeCropRatio: (CropRatio) -> Unit = {},
    onChangeAppTheme: (AppTheme) -> Unit = {}
) {
    val appThemes = AppTheme.entries.toTypedArray()
    val cropRations = CropRatio.entries.toTypedArray()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Text(
            text = stringResource(id = R.string.app_theme),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
            appThemes.forEach { appTheme ->
                val title = when (appTheme) {
                    AppTheme.MODE_DAY -> R.string.app_theme_day
                    AppTheme.MODE_NIGHT -> R.string.app_theme_night
                    else -> R.string.app_theme_system
                }
                val icon = when (appTheme) {
                    AppTheme.MODE_DAY -> R.drawable.ic_theme_day
                    AppTheme.MODE_NIGHT -> R.drawable.ic_theme_night
                    else -> R.drawable.ic_theme_auto
                }
                OptionItem(
                    titleId = title,
                    iconId = icon,
                    onClick = {
                        onChangeAppTheme(appTheme)
                    })
            }
        }
        Spacer(modifier = modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.aspect_ratio),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
            cropRations.forEach { cropRatio ->
                val isCustomCropRatio = cropRatio == CropRatio.RATIO_CUSTOM
                val title = if (isCustomCropRatio)
                    stringResource(id = R.string.aspect_ratio_custom)
                else
                    cropRatio.toString()
                OptionItem(
                    title = title,
                    iconId = R.drawable.ic_theme_night,
                    onClick = {
                        onChangeCropRatio(cropRatio)
                    })
            }
        }
    }
}

@Composable
private fun ToolBar(
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            IconButton(onClick = onOptionsClick) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = stringResource(id = R.string.settings),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
    )
}

@Preview(
    name = "App Light mode",
    showBackground = true
)
@Preview(
    name = "App Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun AppScreenPreview() {
    PreviewWithTheme {
        ImageToWallpaperAppContent()
    }
}

@Preview(name = "Options", showBackground = true)
@Composable
fun OptionsPreview() {
    PreviewWithTheme {
        Options()
    }
}
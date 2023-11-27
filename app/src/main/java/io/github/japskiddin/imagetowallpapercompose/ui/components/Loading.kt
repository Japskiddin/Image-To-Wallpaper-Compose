package io.github.japskiddin.imagetowallpapercompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.utils.PreviewWithTheme

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = .5f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = modifier.size(50.dp),
            strokeCap = StrokeCap.Round,
            strokeWidth = 6.dp
        )
        Spacer(modifier = modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.please_wait),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
    }
}

@Preview(name = "Loading", showBackground = true)
@Composable
fun LoadingPreview() {
    PreviewWithTheme {
        Loading()
    }
}
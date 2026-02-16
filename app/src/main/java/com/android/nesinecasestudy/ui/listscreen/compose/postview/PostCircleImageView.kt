package com.android.nesinecasestudy.ui.listscreen.compose.postview

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.nesinecasestudy.R

@Composable
fun PostCircleImageView(
    imageURL: String,
    contentDescription: String
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageURL)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_placeholder),
        error = painterResource(R.drawable.ic_error_placeholder)
    )
}
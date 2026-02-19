package com.android.nesinecasestudy.ui.listscreen.compose.postitemview

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.nesinecasestudy.R
import com.android.nesinecasestudy.domain.utils.ImageConstants
import com.android.nesinecasestudy.domain.utils.emptyString
import com.android.nesinecasestudy.ui.listscreen.model.PostItemUiModel
import com.android.nesinecasestudy.ui.theme.darkGray

@Composable
fun PostComposeView(
    post: PostItemUiModel,
    modifier: Modifier = Modifier,
    deletePost: (Int) -> Unit,
    onClick: (String, String) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { distance -> distance * 0.8f }
    )

    LaunchedEffect(dismissState.currentValue) {
        when (dismissState.currentValue) {
            SwipeToDismissBoxValue.EndToStart,
            SwipeToDismissBoxValue.StartToEnd -> {
                deletePost(post.id)
                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
            }
            else -> {}
        }
    }

    LaunchedEffect(post.id) {
        if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    val imageUrl = remember(post.id) {
        ImageConstants.buildGrayscaleImage(post.id)
    }

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            DeleteBackground(dismissState)
        },
        content = {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { onClick(post.title, post.body) },
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PostCircleImageView(imageUrl, post.title)

                    Spacer(Modifier.width(16.dp))

                    Column(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = post.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = darkGray,
                            fontWeight = FontWeight.W800,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = post.body,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W400,
                            color = darkGray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun DeleteBackground(
    dismissState: SwipeToDismissBoxState
) {
    val direction = dismissState.dismissDirection
    val progress = dismissState.progress.coerceIn(0f, 1f)

    val alignment = when (direction) {
        SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
        SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
        else -> Alignment.Center
    }

    val animatedColor by animateColorAsState(
        targetValue = Color.Red.copy(alpha = 0.4f + (0.6f * progress)),
        animationSpec = tween(durationMillis = 100),
        label = String().emptyString()
    )

    val scale by animateFloatAsState(
        targetValue = 0.8f + (0.4f * progress),
        label = String().emptyString()
    )

    val alpha by animateFloatAsState(
        targetValue = progress,
        label = String().emptyString()
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedColor)
            .padding(horizontal = 24.dp),
        contentAlignment = alignment
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_delete),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .scale(scale)
                .alpha(alpha)
        )
    }
}
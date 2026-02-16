package com.android.nesinecasestudy.ui.listscreen.compose.postitemview

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.nesinecasestudy.domain.utils.ImageConstants
import com.android.nesinecasestudy.ui.listscreen.model.PostItemUiModel
import com.android.nesinecasestudy.ui.theme.darkGray

@Composable
fun PostComposeView(
    post: PostItemUiModel,
    modifier: Modifier = Modifier,
    deletePost: (Int) -> Unit,
    onClick: (Int, String, String) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()

    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart
            || dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd
        ) {
            deletePost(post.id)
            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    val imageUrl = remember(post.id) {
        ImageConstants.buildGrayscaleImage(post.id)
    }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            DeleteBackground()
        },
        content = {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { onClick(post.id, post.title, post.body) },
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
fun DeleteBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {}
}
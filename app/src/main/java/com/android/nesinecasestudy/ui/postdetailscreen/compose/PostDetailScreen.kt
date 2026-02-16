package com.android.nesinecasestudy.ui.postdetailscreen.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.nesinecasestudy.ui.postdetailscreen.PostDetailScreenEvent
import com.android.nesinecasestudy.ui.postdetailscreen.PostDetailScreenIntent
import com.android.nesinecasestudy.ui.postdetailscreen.vm.PostDetailViewModel
import com.android.nesinecasestudy.ui.theme.darkGray
import com.android.nesinecasestudy.ui.theme.lightGray

@Composable
fun PostDetailScreen(
    viewModel: PostDetailViewModel = hiltViewModel(),
    title: String,
    body: String,
    onNavigateBack: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(title, body) {
        viewModel.onIntent(
            PostDetailScreenIntent.InitialTitleAndText(
                TextFieldValue(title),
                TextFieldValue(body)
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            if (event is PostDetailScreenEvent.NavigateBack) {
                onNavigateBack()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
            .padding(16.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = uiState.titleText,
            onValueChange = {
                viewModel.onIntent(
                    PostDetailScreenIntent.TitleTextChanged(it)
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Black,
                focusedContainerColor = lightGray,
                unfocusedContainerColor = darkGray.copy(alpha = 0.2f)
            )
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.bodyText,
            onValueChange = {
                viewModel.onIntent(
                    PostDetailScreenIntent.BodyTextChanged(it)
                )
            },
            singleLine = false,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Black,
                focusedContainerColor = lightGray,
                unfocusedContainerColor = darkGray.copy(alpha = 0.2f)
            )
        )
    }

    BackHandler {
        viewModel.onIntent(PostDetailScreenIntent.NavigateBackClicked)
    }
}
package com.android.nesinecasestudy.ui.homescreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.android.nesinecasestudy.R
import com.android.nesinecasestudy.ui.homescreen.vm.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onFinishApp: () -> Unit,
    navigateToXMLDesign: () -> Unit,
    navigateToComposeDesign: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                HomeScreenEvent.FinishApp -> {
                    onFinishApp()
                }

                HomeScreenEvent.NavigateToComposeDesign -> {
                    navigateToComposeDesign()
                }

                HomeScreenEvent.NavigateToXMLDesign -> {
                    navigateToXMLDesign()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                viewModel.onIntent(HomeScreenIntent.XMLDesignClicked)
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors()
        ) {
            Text(
                text = stringResource(R.string.xml_design),
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                viewModel.onIntent(HomeScreenIntent.ComposeDesignClicked)
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors()
        ) {
            Text(
                text = stringResource(R.string.compose_design),
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }

    BackHandler { viewModel.onIntent(HomeScreenIntent.FinishApp) }
}
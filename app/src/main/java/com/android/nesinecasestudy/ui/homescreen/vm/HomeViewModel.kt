package com.android.nesinecasestudy.ui.homescreen.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.nesinecasestudy.ui.homescreen.HomeScreenEvent
import com.android.nesinecasestudy.ui.homescreen.HomeScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<HomeScreenEvent>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val event: SharedFlow<HomeScreenEvent> = _event.asSharedFlow()

    fun onIntent(intent: HomeScreenIntent) {
        when (intent) {
            HomeScreenIntent.XMLDesignClicked -> {
                emitEvent(HomeScreenEvent.NavigateToXMLDesign)
            }
            HomeScreenIntent.ComposeDesignClicked -> {
                emitEvent(HomeScreenEvent.NavigateToComposeDesign)
            }
            HomeScreenIntent.FinishApp -> {
                emitEvent(HomeScreenEvent.FinishApp)
            }
        }
    }

    private fun emitEvent(event: HomeScreenEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}
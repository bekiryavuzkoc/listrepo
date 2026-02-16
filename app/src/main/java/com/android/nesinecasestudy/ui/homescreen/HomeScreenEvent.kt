package com.android.nesinecasestudy.ui.homescreen

sealed interface HomeScreenEvent {
    object FinishApp : HomeScreenEvent
    object NavigateToXMLDesign  : HomeScreenEvent
    object NavigateToComposeDesign  : HomeScreenEvent
}
package com.android.nesinecasestudy.ui.homescreen

sealed interface HomeScreenIntent {
    data object FinishApp: HomeScreenIntent
    data object XMLDesignClicked : HomeScreenIntent
    data object ComposeDesignClicked: HomeScreenIntent
}
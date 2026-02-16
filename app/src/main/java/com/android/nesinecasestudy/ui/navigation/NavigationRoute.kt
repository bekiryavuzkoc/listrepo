package com.android.nesinecasestudy.ui.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
sealed class NavigationRoute : Parcelable {

    @Serializable
    @Parcelize
    data object Home : NavigationRoute()

    @Serializable
    @Parcelize
    data object ComposeDesignScreen : NavigationRoute()

    @Serializable
    @Parcelize
    data object XMLDesignScreen : NavigationRoute()

    @Serializable
    @Parcelize
    data class ComposePostDetailScreen(val id: Int, val title: String, val detail: String) : NavigationRoute()
}
package com.android.nesinecasestudy.domain.utils

object ImageConstants {

    private const val IMAGE_BASE_URL = "https://picsum.photos"
    private const val SIZE = "300/300"

    fun buildGrayscaleImage(id: Int): String {
        return "$IMAGE_BASE_URL/$SIZE?random=$id&grayscale"
    }
}
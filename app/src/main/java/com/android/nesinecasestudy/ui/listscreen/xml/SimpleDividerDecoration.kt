package com.android.nesinecasestudy.ui.listscreen.xml

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class SimpleDividerDecoration(
    context: Context
) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        color = Color.parseColor("#E0E0E0")
        strokeWidth = context.resources.displayMetrics.density
    }

    override fun onDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val left = parent.paddingLeft.toFloat()
        val right = (parent.width - parent.paddingRight).toFloat()

        val childCount = parent.childCount

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val y = child.bottom.toFloat()
            c.drawLine(left, y, right, y, paint)
        }
    }
}
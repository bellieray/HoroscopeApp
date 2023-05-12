package com.eray.horoscopeapp.components

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Add this key as tag to a view in order this item decoration to skip adding horizontal padding.
const val SKIP_HORIZONTAL_PADDING_KEY = "skipHorizontalPadding"

class BaseVerticalDividerGridItemDecoration(
    private val rowSpacing: Int = 0,
    private val columnSpacing: Int = 0,
    private val edgeSpacingVertical: Int = 0,
    private val edgeSpacingHorizontal: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildLayoutPosition(view)
        val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val spanIndex = layoutParams.spanIndex
        val spanSize = layoutParams.spanSize

        // horizontal
        if (view.tag != SKIP_HORIZONTAL_PADDING_KEY) {
            if (spanCount == spanSize) {
                outRect.left = edgeSpacingHorizontal
                outRect.right = edgeSpacingHorizontal
            } else {
                when (spanIndex) {
                    0 -> {
                        outRect.left = edgeSpacingHorizontal
                        outRect.right = columnSpacing / 2
                    }
                    spanCount - 1 -> {
                        outRect.left = columnSpacing / 2
                        outRect.right = edgeSpacingHorizontal
                    }
                    else -> {
                        outRect.left = columnSpacing / 2
                        outRect.right = columnSpacing / 2
                    }
                }
            }
        }
        // vertical
        when (position) {
            0 -> {
                outRect.top = edgeSpacingVertical
                outRect.bottom = rowSpacing / 2
            }
            1 -> {
                if (spanIndex == 0) {
                    outRect.top = rowSpacing / 2
                    outRect.bottom = rowSpacing / 2
                } else {
                    outRect.top = edgeSpacingVertical
                    outRect.bottom = rowSpacing / 2
                }
            }
            (parent.adapter?.itemCount ?: 0) - 1 -> {
                outRect.top = rowSpacing / 2
                outRect.bottom = edgeSpacingVertical
            }
            (parent.adapter?.itemCount ?: 0) - 2 -> {
                if (spanIndex == 0) {
                    outRect.top = rowSpacing / 2
                    outRect.bottom = edgeSpacingVertical
                } else {
                    outRect.top = rowSpacing / 2
                    outRect.bottom = rowSpacing / 2
                }
            }
            else -> {
                outRect.top = rowSpacing / 2
                outRect.bottom = rowSpacing / 2
            }
        }
    }
}
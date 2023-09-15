package com.example.tat.features.project.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.max
import kotlin.math.min

class MyCustomViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val firstChild: View?
        get() = getChildAt(0)
    private val secondChild: View?
        get() = getChildAt(1)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val childWidthSpec = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.UNSPECIFIED -> widthMeasureSpec
            MeasureSpec.AT_MOST -> widthMeasureSpec
            MeasureSpec.EXACTLY -> widthMeasureSpec
            else -> error("Unreachable")
        }
        val childHeightSpec = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.UNSPECIFIED -> heightMeasureSpec
            MeasureSpec.AT_MOST -> heightMeasureSpec
            MeasureSpec.EXACTLY -> heightMeasureSpec
            else -> error("Unreachable")
        }

        firstChild?.measure(childWidthSpec, childHeightSpec)
        var firstWidth = firstChild?.measuredWidth ?: 0
        var firstHeight = firstChild?.measuredHeight ?: 0

        var valueForFirstWidth = firstWidth
        var valueForFirstHeight = firstHeight

        if (firstWidth > 75 || firstHeight > 75) {
            val newFirst = MeasureSpec.makeMeasureSpec(75, MeasureSpec.EXACTLY)
            firstChild?.measure(newFirst, newFirst)
            valueForFirstWidth = 75
            valueForFirstHeight = 75
        }

        secondChild?.measure(childWidthSpec, childHeightSpec)
        val secondWidth = secondChild?.measuredWidth ?: 0
        val secondHeight = secondChild?.measuredHeight ?: 0
        val newSecondWidth = MeasureSpec.makeMeasureSpec(widthSize - valueForFirstWidth, MeasureSpec.EXACTLY)
        secondChild?.measure(newSecondWidth, childHeightSpec)

        val width = if (valueForFirstWidth + secondWidth < widthSize) {
            valueForFirstWidth + secondWidth
        } else {
            widthSize
        }

        val height = max(valueForFirstHeight, secondHeight)
        setMeasuredDimension(width, height)
    }

//    EXACTLY (точно) — размер View должен быть задан точно (например, в dp или px).
//        Это может быть указано в макете View с атрибутом android:layout_width или android:layout_height со значением фиксированной ширины или высоты.
//    AT_MOST (не больше) — View может быть любого размера, который не превышает указанный максимальный размер,
//        например, layout_width="wrap_content". Это означает, что View может иметь любой размер, пока он не превышает размер родительского контейнера.
//    UNSPECIFIED (неопределенный) — размер View может быть любым, не ограниченным размером родителя.


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if(secondChild!!.measuredHeight > 75) {
            firstChild?.layout(
                0,
                (b - 75) / 2,
                (firstChild?.measuredWidth ?: 0),
                ((b - 75) / 2) + (firstChild?.measuredHeight ?: 0)
            )
            secondChild?.layout(
                (firstChild?.measuredWidth ?: 0),
                0,
                r,
                b
            )
        } else {
            firstChild?.layout(
                0,
                0,
                firstChild?.measuredWidth ?: 0,
                firstChild?.measuredHeight ?: 0
            )
            secondChild?.layout(
                firstChild?.measuredWidth ?: 0,
                (b - (secondChild?.measuredHeight ?: 0)) / 2,
                r,
                ((b - (firstChild?.measuredHeight ?: 0)) / 2) + (firstChild?.measuredHeight ?: 0)
            )
        }
    }
}
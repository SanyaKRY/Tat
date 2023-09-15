package com.example.tat.features.project.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import androidx.core.view.marginTop
import com.example.tat.R

import kotlin.math.max

class CustomViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val firstChild: View?
        get() = if (childCount > 0) getChildAt(0) else null
    private val secondChild: View?
        get() = if (childCount > 1) getChildAt(1) else null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        checkChildCount()

        firstChild?.let { measureChild(it, widthMeasureSpec) }
        secondChild?.let { measureChild(it, widthMeasureSpec) }

        val firstWidth = firstChild?.measuredWidth ?: 0
        val firstHeight = firstChild?.measuredHeight ?: 0
        val secondWidth = secondChild?.measuredWidth ?: 0
        val secondHeight = secondChild?.measuredHeight ?: 0

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val childrenOnSameLine = firstWidth + secondWidth < widthSize || widthMode == MeasureSpec.UNSPECIFIED

        //ширина
        val width = when (widthMode) {
            MeasureSpec.UNSPECIFIED -> firstWidth + secondWidth
            MeasureSpec.EXACTLY -> widthSize

            MeasureSpec.AT_MOST -> {
                if (childrenOnSameLine) {
                    firstWidth + secondWidth
                } else {
                    max(firstWidth, secondWidth)
                }
            }

            else -> error("Unreachable")
        }

        //высота
        val height = if (childrenOnSameLine) {
            max(firstHeight, secondHeight)
        } else {
            firstHeight + secondHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        firstChild?.layout(
            0,
            0,
            firstChild?.measuredWidth ?: 0,
            firstChild?.measuredHeight ?: 0
        )
        secondChild?.layout(
            r - l - (secondChild?.measuredWidth ?: 0),
            b - t - (secondChild?.measuredHeight ?: 0),
            r - l,
            b - t
        )
    }

    private fun measureChild(child: View, widthMeasureSpec: Int) {
        val specSize = MeasureSpec.getSize(widthMeasureSpec)

        val childWidthSpec = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.UNSPECIFIED -> widthMeasureSpec
            MeasureSpec.AT_MOST -> widthMeasureSpec
            MeasureSpec.EXACTLY -> MeasureSpec.makeMeasureSpec(specSize, MeasureSpec.AT_MOST)
            else -> error("Unreachable")
        }
        val childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)

        child.measure(childWidthSpec, childHeightSpec)
    }

    private fun checkChildCount() {
        if (childCount > 2) error("CustomViewGroup should not contain more than 2 children")
    }










//    private val projectFolderImage: View? = null
//    private val projectName: View? = null
//    private val textViewNumberOfSuite: View? = null
//    private val NumberOfSuite: View? = null
//
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//
////        measureChild() - Попросите одного из дочерних элементов этого представления измерить себя,
////        учитывая как требования MeasureSpec для этого представления, так и его заполнение.
//        // измеряем наше вью
//        measureChild(projectFolderImage, widthMeasureSpec, heightMeasureSpec)
//        measureChild(projectName, widthMeasureSpec, heightMeasureSpec)
//        measureChild(textViewNumberOfSuite, widthMeasureSpec, heightMeasureSpec)
//        measureChild(NumberOfSuite, widthMeasureSpec, heightMeasureSpec)
//
//        // сетаем измерненые значения
//        val projectFolderImageWidth = projectFolderImage?.measuredWidth
//        val projectFolderImageHeight = projectFolderImage?.measuredHeight
//
//
////        widthMeasureSpec и heightMeasureSpec. Это целочисленные значения в которых
////        закодирована информация о предпочтениях layout к размерам view
//
////        MeasureSpec — это класс, используемый для определения размеров View в Android.
////        Когда View помещается на экран, ей нужно знать, какое место ей предоставлено,
////        чтобы правильно расположиться и отобразиться. MeasureSpec состоит
////                из двух основных компонентов: размера и режима измерения.
//
//        // getSize() - извлекает размер из заданного объекта MeasureSpec
//        // getMode() - извлекает режим из заданного объекта MeasureSpec
//        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
//        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
//        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
//        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
//
//        val width = when (widthMode) {
//            MeasureSpec.UNSPECIFIED -> { widthSize }
//            MeasureSpec.EXACTLY -> { widthSize }
//            MeasureSpec.AT_MOST -> { widthSize }
//            else -> error("Unreachable")
//        }
//
//        val height = when (heightMode) {
//            MeasureSpec.UNSPECIFIED -> { heightSize }
//            MeasureSpec.EXACTLY -> { heightSize }
//            MeasureSpec.AT_MOST -> { heightSize }
//            else -> error("Unreachable")
//        }
//
//        // Устанавливаем фактический размер View
//        setMeasuredDimension(width, height)
//    }
//
//    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        projectFolderImage?.layout(left, top, right, bottom)
//    }
}
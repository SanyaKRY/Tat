package com.example.tat.features.project.presentation.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import com.example.tat.R

class SmallCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var smallpass: Int = 25
    var smallfailed: Int = 52
    var smallskipped: Int = 38
    val listOfTest = mutableListOf<Test>()

    class Test constructor(val value: Int, val name: String, val color: Int)
//    val listOfTest = listOf<Test>(Test(55, "GREEN",Color.GREEN),
//        Test(120, "RED", Color.RED),
//        Test(550, "YELLOW", Color.YELLOW))


    val linePaint = Paint().apply {
        this.style = Paint.Style.STROKE
        this.strokeWidth = 3F
        this.color = context.resources.getColor(R.color.black)
    }

    val fillpaint = Paint().apply {
        this.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        listOfTest.add(Test(smallpass, "PASSED", R.color.dark_green))
        listOfTest.add(Test(smallfailed, "FAILED", R.color.dark_red))
        listOfTest.add(Test(smallskipped, "SKIPPED", R.color.dark_yellow))
//        listOfTest.addAll(pass, skipped, failed)

        val textPaintonCircle = Paint()
        textPaintonCircle.textSize = 40F
        textPaintonCircle.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
        textPaintonCircle.textAlign = Paint.Align.CENTER

        val canterX = (width / 2).toFloat() // центр x
        val canterY = (height / 2).toFloat() // центр y

//Рисуем круг сноружи и обводим его
        val rectF = RectF()
        rectF.set(canterX - RADIUS, canterY - RADIUS, canterX + RADIUS, canterY + RADIUS)
        var totalValues = 0
        listOfTest.forEach {
            totalValues += it.value
        }
        var startAngle : Float = 0F
        listOfTest.forEach {
            val sweapAngle : Float = 360F * it.value / totalValues
            var path: Path = Path()
            path.moveTo(canterX, canterY)
            path.arcTo(rectF, startAngle, sweapAngle, false)
            path.close()
            fillpaint.color = context.resources.getColor(it.color)
            canvas.drawPath(path, fillpaint)
            canvas.drawPath(path, linePaint)
            startAngle += sweapAngle
        }

//Рисуем круг и обводим его в центре
        var interanalOvalPath: Path = Path()
        interanalOvalPath.addCircle(canterX, canterY,RADIUS * 0.5f , Path.Direction.CW)

        val emptyPaint = Paint().apply {
            this.style = Paint.Style.FILL
            this.color = context.resources.getColor(R.color.light_gray)
        }
        canvas.drawPath(interanalOvalPath, emptyPaint)
        canvas.drawPath(interanalOvalPath, linePaint)


//        var x: Float = RADIUS * cos((Math.PI * 135 / 180)).toFloat()
//        var y: Float = RADIUS * sin((Math.PI * 135 / 180)).toFloat()
//        canvas.drawText("Hello", x+ canterX, y+canterY, textPaintonCircle)


//        canvas.restore()

//        canvas.translate(canterX, canterY)
//
//        var startAngle1 : Float = 0F
//        listOfTest.forEach {
//            val sweapAngle: Float = 360F * it.value / totalValues
//
//            var x: Float = RADIUS * cos(startAngle1 + sweapAngle/2)
//            var y: Float = RADIUS * sin(startAngle1 + sweapAngle/2)
//
//            canvas.drawT
//
//            startAngle1 += sweapAngle
//        }
    }


    fun setCurrent(pass: Int, failed: Int, skipped: Int ) {
        this.smallpass = pass
        this.smallfailed = failed
        this.smallskipped = skipped
        invalidate()
    }


    private companion object {
        private const val RADIUS = 80F
    }
}
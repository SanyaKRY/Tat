package com.example.tat.features.project.presentation.ui

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.AttrRes
import com.example.tat.R

// https://medium.com/@paulnunezm/canvas-animations-simple-circle-progress-view-on-android-8309900ab8ed
class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var pass: Int = 0
    var failed: Int = 0
    var skipped: Int = 0

    init {
        if (attrs != null) {
            val styledAttrs = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.CustomView,
                defStyleAttr,
                0
            )
            val pass: Int = styledAttrs.getInt(R.styleable.CustomView_pass, 5)
            val failed: Int = styledAttrs.getInt(R.styleable.CustomView_failed, 5)
            val skipped: Int = styledAttrs.getInt(R.styleable.CustomView_skipped, 5)

            this.pass = pass
            this.failed = failed
            this.skipped = skipped

            // неободимо
            styledAttrs.recycle()
        }
    }

    companion object {
        private const val RADIUS = 200F
        const val ANGLE_VALUE_HOLDER = "age"
    }

    private var currentAngle = 0F



//    private val parentArcPaintAnother = Paint().apply {
//        style = Paint.Style.STROKE
//        isAntiAlias = true
//        color = Color.RED
//        strokeWidth = 40f
//    }
//    private val fillArcPaint = Paint().apply {
//        style = Paint.Style.STROKE
//        isAntiAlias = true
//        color = context.resources?.getColor(R.color.teal_200, null)!!
//        strokeWidth = 40f
//    }

    val linePaint = Paint().apply {
        this.style = Paint.Style.STROKE
        this.strokeWidth = 5F
        this.color = Color.BLACK
    }

    val fillpaint = Paint().apply {
        this.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            drawPass(it)
        }
    }

    val textPaintonCircle1 = Paint().apply {
        textSize = 50F
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
        textAlign = Paint.Align.CENTER
    }

    val textPaintonCircle = Paint().apply {
        textSize = 40F
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
        textAlign = Paint.Align.CENTER
//        setShadowLayer(1F, 5F, 5F, context.resources.getColor(R.color.purple))
    }

    fun drawPass(canvas: Canvas?) {
        val canterX = (width / 2).toFloat() // центр x
        val canterY = (height / 2).toFloat() // центр y

        val path1: Path = Path()
        val path2: Path = Path()
        val path3: Path = Path()

        val totalValue = pass+failed+skipped

        var startAngle : Float = 0F

        val sweapAnglePass : Float = 360F * pass / totalValue
        val sweapAngleFailed : Float = 360F * failed / totalValue
        val sweapAngleSkipped : Float = 360F * skipped / totalValue

        if (currentAngle>=0 && currentAngle<=sweapAnglePass) {
            path1.moveTo(canterX, canterY)
            val rectFPassed = RectF()
            fillpaint.color = Color.GREEN
            rectFPassed.set(canterX - RADIUS*1.1f, canterY - RADIUS*1.1f, canterX + RADIUS*1.1f, canterY + RADIUS*1.1f)
            path1.arcTo(rectFPassed, 0F, currentAngle, false)
            path1.close()
            canvas?.drawPath(path1, fillpaint)
            canvas?.drawPath(path1, linePaint)

            val posForNewString = textPaintonCircle.descent() - textPaintonCircle.ascent()

            var x: Float = 0.75F * RADIUS * StrictMath.cos((Math.PI * (startAngle + currentAngle / 2) / 180))
                .toFloat()
            var y: Float = 0.75F * RADIUS * StrictMath.sin((Math.PI * (startAngle + currentAngle / 2) / 180))
                .toFloat()
            canvas?.drawText(pass.toString(), x+ canterX, y+canterY +posForNewString/4, textPaintonCircle1)

            var xt: Float = 1.50F * RADIUS * StrictMath.cos((Math.PI * (startAngle + currentAngle / 2) / 180))
                .toFloat()
            var yt: Float = 1.50F * RADIUS * StrictMath.sin((Math.PI * (startAngle + currentAngle / 2) / 180))
                .toFloat()
            canvas?.drawText("PASSED", xt+ canterX, yt+canterY+posForNewString/4 , textPaintonCircle)
        }
        if (currentAngle>sweapAnglePass && currentAngle <= (sweapAnglePass+sweapAngleFailed)) {
            path1.moveTo(canterX, canterY)
            val rectFPassed = RectF()
            fillpaint.color = Color.GREEN
            rectFPassed.set(canterX - RADIUS*1.1f, canterY - RADIUS*1.1f, canterX + RADIUS*1.1f, canterY + RADIUS*1.1f)
            path1.arcTo(rectFPassed, 0F, sweapAnglePass, false)
            path1.close()
            canvas?.drawPath(path1, fillpaint)
            canvas?.drawPath(path1, linePaint)

            val posForNewString = textPaintonCircle.descent() - textPaintonCircle.ascent()

            var x: Float = 0.75F * RADIUS * StrictMath.cos((Math.PI * (startAngle + sweapAnglePass / 2) / 180))
                .toFloat()
            var y: Float = 0.75F * RADIUS * StrictMath.sin((Math.PI * (startAngle + sweapAnglePass / 2) / 180))
                .toFloat()
            canvas?.drawText(pass.toString(), x+ canterX, y+canterY +posForNewString/4, textPaintonCircle1)

            var xt: Float = 1.50F * RADIUS * StrictMath.cos((Math.PI * (startAngle + sweapAnglePass / 2) / 180))
                .toFloat()
            var yt: Float = 1.50F * RADIUS * StrictMath.sin((Math.PI * (startAngle + sweapAnglePass / 2) / 180))
                .toFloat()
            canvas?.drawText("PASSED", xt+ canterX, yt+canterY+posForNewString/4 , textPaintonCircle)





            path2.moveTo(canterX, canterY)
            val rectF = RectF()
            fillpaint.color = Color.RED
            rectF.set(canterX - RADIUS, canterY - RADIUS, canterX + RADIUS, canterY + RADIUS)
            path2.arcTo(rectF, sweapAnglePass, currentAngle-sweapAnglePass, false)
            path2.close()
            canvas?.drawPath(path2, fillpaint)
            canvas?.drawPath(path2, linePaint)

//            val posForNewString = textPaintonCircle.descent() - textPaintonCircle.ascent()

            var xf: Float = 0.75F * RADIUS * StrictMath.cos((Math.PI * (sweapAnglePass + (currentAngle-sweapAnglePass) / 2) / 180))
                .toFloat()
            var yf: Float = 0.75F * RADIUS * StrictMath.sin((Math.PI * (sweapAnglePass + (currentAngle-sweapAnglePass) / 2) / 180))
                .toFloat()
            canvas?.drawText(failed.toString(), xf+ canterX, yf+canterY +posForNewString/4, textPaintonCircle1)

            var xtf: Float = 1.50F * RADIUS * StrictMath.cos((Math.PI * (sweapAnglePass + (currentAngle-sweapAnglePass)/2) / 180))
                .toFloat()
            var ytf: Float = 1.50F * RADIUS * StrictMath.sin((Math.PI * (sweapAnglePass + (currentAngle-sweapAnglePass)/2) / 180))
                .toFloat()
            canvas?.drawText("FAILED", xtf+ canterX, ytf+canterY+posForNewString/4 , textPaintonCircle)
        }

        if ((sweapAnglePass+sweapAngleFailed)<currentAngle && currentAngle <= (sweapAnglePass+sweapAngleFailed+sweapAngleFailed) ) {
            path1.moveTo(canterX, canterY)
            val rectFPassed = RectF()
            fillpaint.color = Color.GREEN
            rectFPassed.set(canterX - RADIUS*1.1f, canterY - RADIUS*1.1f, canterX + RADIUS*1.1f, canterY + RADIUS*1.1f)
            path1.arcTo(rectFPassed, 0F, sweapAnglePass, false)
            path1.close()
            canvas?.drawPath(path1, fillpaint)
            canvas?.drawPath(path1, linePaint)

            val posForNewString = textPaintonCircle.descent() - textPaintonCircle.ascent()
            var x: Float = 0.75F * RADIUS * StrictMath.cos((Math.PI * (startAngle + sweapAnglePass / 2) / 180))
                .toFloat()
            var y: Float = 0.75F * RADIUS * StrictMath.sin((Math.PI * (startAngle + sweapAnglePass / 2) / 180))
                .toFloat()
            canvas?.drawText(pass.toString(), x+ canterX, y+canterY +posForNewString/4, textPaintonCircle1)

            var xt: Float = 1.50F * RADIUS * StrictMath.cos((Math.PI * (startAngle + sweapAnglePass / 2) / 180))
                .toFloat()
            var yt: Float = 1.50F * RADIUS * StrictMath.sin((Math.PI * (startAngle + sweapAnglePass / 2) / 180))
                .toFloat()
            canvas?.drawText("PASSED", xt+ canterX, yt+canterY+posForNewString/4 , textPaintonCircle)


            path2.moveTo(canterX, canterY)
            val rectF = RectF()
            fillpaint.color = Color.RED
            rectF.set(canterX - RADIUS, canterY - RADIUS, canterX + RADIUS, canterY + RADIUS)
            path2.arcTo(rectF, sweapAnglePass, sweapAngleFailed, false)
            path2.close()
            canvas?.drawPath(path2, fillpaint)
            canvas?.drawPath(path2, linePaint)

            var xf: Float = 0.75F * RADIUS * StrictMath.cos((Math.PI * (sweapAnglePass+ (sweapAngleFailed) / 2) / 180))
                .toFloat()
            var yf: Float = 0.75F * RADIUS * StrictMath.sin((Math.PI * (sweapAnglePass+ (sweapAngleFailed) / 2) / 180))
                .toFloat()
            canvas?.drawText(failed.toString(), xf+ canterX, yf+canterY +posForNewString/4, textPaintonCircle1)
            var xtf: Float = 1.50F * RADIUS * StrictMath.cos((Math.PI * (sweapAnglePass+ (sweapAngleFailed) / 2) / 180))
                .toFloat()
            var ytf: Float = 1.50F * RADIUS * StrictMath.sin((Math.PI * (sweapAnglePass+ (sweapAngleFailed) / 2) / 180))
                .toFloat()
            canvas?.drawText("FAILED", xtf+ canterX, ytf+canterY+posForNewString/4 , textPaintonCircle)


            path3.moveTo(canterX, canterY)
//            val rectF = RectF()
            fillpaint.color = Color.YELLOW
//            rectF.set(canterX - RADIUS*1.1f, canterY - RADIUS*1.1f, canterX + RADIUS*1.1f, canterY + RADIUS*1.1f)
            path3.arcTo(rectF, (sweapAnglePass+sweapAngleFailed), currentAngle-(sweapAnglePass+sweapAngleFailed), false)
            path3.close()
            canvas?.drawPath(path3, fillpaint)
            canvas?.drawPath(path3, linePaint)

            var xs: Float = 0.75F * RADIUS * StrictMath.cos((Math.PI * (sweapAnglePass+sweapAngleFailed + (currentAngle-sweapAnglePass-sweapAngleFailed) / 2) / 180))
                .toFloat()
            var ys: Float = 0.75F * RADIUS * StrictMath.sin((Math.PI * (sweapAnglePass +sweapAngleFailed+ (currentAngle-sweapAnglePass-sweapAngleFailed) / 2) / 180))
                .toFloat()
            canvas?.drawText(failed.toString(), xs+ canterX, ys+canterY +posForNewString/4, textPaintonCircle1)

            var xtfs: Float = 1.50F * RADIUS * StrictMath.cos((Math.PI * (sweapAnglePass+sweapAngleFailed + (currentAngle-sweapAnglePass-sweapAngleFailed) / 2) / 180))
                .toFloat()
            var ytfs: Float = 1.50F * RADIUS * StrictMath.sin((Math.PI * (sweapAnglePass+sweapAngleFailed + (currentAngle-sweapAnglePass-sweapAngleFailed) / 2) / 180))
                .toFloat()
            canvas?.drawText("SKIPPED", xtfs+ canterX, ytfs+canterY+posForNewString/4 , textPaintonCircle)
        }




        if (currentAngle == 360F ) {
            path1.moveTo(canterX, canterY)
            val rectFPassed = RectF()
            fillpaint.color = Color.GREEN
            rectFPassed.set(canterX - RADIUS*1.1f, canterY - RADIUS*1.1f, canterX + RADIUS*1.1f, canterY + RADIUS*1.1f)
            path1.arcTo(rectFPassed, 0F, sweapAnglePass, false)
            path1.close()
            canvas?.drawPath(path1, fillpaint)
            canvas?.drawPath(path1, linePaint)

            val posForNewString = textPaintonCircle.descent() - textPaintonCircle.ascent()
            var x: Float = 0.75F * RADIUS * StrictMath.cos((Math.PI * (startAngle + sweapAnglePass / 2) / 180))
                .toFloat()
            var y: Float = 0.75F * RADIUS * StrictMath.sin((Math.PI * (startAngle + sweapAnglePass / 2) / 180))
                .toFloat()
            canvas?.drawText(pass.toString(), x+ canterX, y+canterY +posForNewString/4, textPaintonCircle1)

            var xt: Float = 1.50F * RADIUS * StrictMath.cos((Math.PI * (startAngle + sweapAnglePass / 2) / 180))
                .toFloat()
            var yt: Float = 1.50F * RADIUS * StrictMath.sin((Math.PI * (startAngle + sweapAnglePass / 2) / 180))
                .toFloat()
            canvas?.drawText("PASSED", xt+ canterX, yt+canterY+posForNewString/4 , textPaintonCircle)


            path2.moveTo(canterX, canterY)
            val rectF = RectF()
            fillpaint.color = Color.RED
            rectF.set(canterX - RADIUS, canterY - RADIUS, canterX + RADIUS, canterY + RADIUS)
            path2.arcTo(rectF, sweapAnglePass, sweapAngleFailed, false)
            path2.close()
            canvas?.drawPath(path2, fillpaint)
            canvas?.drawPath(path2, linePaint)

            var xf: Float = 0.75F * RADIUS * StrictMath.cos((Math.PI * (sweapAnglePass+ (sweapAngleFailed) / 2) / 180))
                .toFloat()
            var yf: Float = 0.75F * RADIUS * StrictMath.sin((Math.PI * (sweapAnglePass+ (sweapAngleFailed) / 2) / 180))
                .toFloat()
            canvas?.drawText(failed.toString(), xf+ canterX, yf+canterY +posForNewString/4, textPaintonCircle1)
            var xtf: Float = 1.50F * RADIUS * StrictMath.cos((Math.PI * (sweapAnglePass+ (sweapAngleFailed) / 2) / 180))
                .toFloat()
            var ytf: Float = 1.50F * RADIUS * StrictMath.sin((Math.PI * (sweapAnglePass+ (sweapAngleFailed) / 2) / 180))
                .toFloat()
            canvas?.drawText("FAILED", xtf+ canterX, ytf+canterY+posForNewString/4 , textPaintonCircle)


            path3.moveTo(canterX, canterY)
//            val rectF = RectF()
            fillpaint.color = Color.YELLOW
//            rectF.set(canterX - RADIUS*1.1f, canterY - RADIUS*1.1f, canterX + RADIUS*1.1f, canterY + RADIUS*1.1f)
            path3.arcTo(rectF, (sweapAnglePass+sweapAngleFailed), sweapAngleSkipped, false)
            path3.close()
            canvas?.drawPath(path3, fillpaint)
            canvas?.drawPath(path3, linePaint)

            var xs: Float = 0.75F * RADIUS * StrictMath.cos((Math.PI * (sweapAnglePass+sweapAngleFailed + (sweapAngleSkipped) / 2) / 180))
                .toFloat()
            var ys: Float = 0.75F * RADIUS * StrictMath.sin((Math.PI * (sweapAnglePass +sweapAngleFailed+ (sweapAngleSkipped) / 2) / 180))
                .toFloat()
            canvas?.drawText(skipped.toString(), xs+ canterX, ys+canterY +posForNewString/4, textPaintonCircle1)

            var xtfs: Float = 1.50F * RADIUS * StrictMath.cos((Math.PI * (sweapAnglePass+sweapAngleFailed + (sweapAngleSkipped) / 2) / 180))
                .toFloat()
            var ytfs: Float = 1.50F * RADIUS * StrictMath.sin((Math.PI * (sweapAnglePass+sweapAngleFailed + (sweapAngleSkipped) / 2) / 180))
                .toFloat()
            canvas?.drawText("SKIPPED", xtfs+ canterX, ytfs+canterY+posForNewString/4 , textPaintonCircle)
        }


//Рисуем круг и обводим его в центре
        var interanalOvalPath: Path = Path()
        interanalOvalPath.addCircle(canterX, canterY,RADIUS * 0.5f , Path.Direction.CW)

        val emptyPaint = Paint().apply {
            this.style = Paint.Style.FILL
            this.color = Color.WHITE
        }
        canvas?.drawPath(interanalOvalPath, emptyPaint)
        canvas?.drawPath(interanalOvalPath, linePaint)


        //Рисуем текст в центре
        val textPaint = Paint()
        textPaint.textSize = 40F
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
        textPaint.textAlign = Paint.Align.CENTER
        val textString: String = "Total Test:"
        canvas?.drawText(textString, canterX, canterY, textPaint)

        val posForNewString =textPaint.descent() - textPaint.ascent()
        val textNumber: String = totalValue.toString()
        canvas?.drawText(textNumber, canterX, canterY +posForNewString, textPaint)

        println(currentAngle)
    }

    private val fillArcPaint1 = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = Color.RED
        strokeWidth = 10f
        strokeCap = Paint.Cap.ROUND
    }

//    private fun setSpace() {
//        val rectF = RectF()
//        rectF.set(canterX - RADIUS, canterY - RADIUS, canterX + RADIUS, canterY + RADIUS)
//        val horizontalCenter = (width.div(2)).toFloat()
//        val verticalCenter = (height.div(2)).toFloat()
//        val ovalSize = 200
//        ovalSpace.set(
//            horizontalCenter - ovalSize,
//            verticalCenter - ovalSize,
//            horizontalCenter + ovalSize,
//            verticalCenter + ovalSize
//        )
//    }

//    private fun drawBackgroundArc(it: Canvas) {
//        it.drawArc(ovalSpace, 0f, 360f, false, parentArcPaint)
//    }

//    private fun drawInnerArc(canvas: Canvas) {
//        val percentageToFill = getCurrentPercentageToFill()
//        canvas.drawArc(ovalSpace, 270f, percentageToFill, false, fillArcPaint)
//    }
//
//    private fun drawInnerArc1(canvas: Canvas) {
//        val percentageToFill = getCurrentPercentageToFill()
//        canvas.drawArc(ovalSpace, 270f, currentAge, false, fillArcPaint1)
//    }
//
//    private fun getCurrentPercentageToFill() =
//        (ARC_FULL_ROTATION_DEGREE * (currentPercentage / PERCENTAGE_DIVIDER)).toFloat()


    fun animateProgress() {
        val valuesHolder = PropertyValuesHolder.ofFloat("age", 0f, 360f)
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            duration = 3_000
            addUpdateListener {
                val angle = it.getAnimatedValue(ANGLE_VALUE_HOLDER) as Float
                currentAngle = angle.toFloat()
                invalidate()
            }
        }
        animator.start()
    }

    fun setCurrent(pass: Int, failed: Int, skipped: Int ) {
        Log.d("setCurrent","pass: $pass failed: $failed skipped: $skipped")
        this.pass = pass
        this.failed = failed
        this.skipped = skipped
        invalidate()
    }
}
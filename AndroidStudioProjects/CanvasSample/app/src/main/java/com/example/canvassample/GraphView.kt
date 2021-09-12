package com.example.canvassample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class GraphView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    data class DataPoint(val x: Int, val y: Int)


    private val dataPointPaint: Paint
    private val dataPointFillPaint: Paint
    private val dataPointLinePaint: Paint
    private val axisLinePaint: Paint
    private var pointList = mutableListOf<DataPoint>()
    private var step = 0f
    private var currentX = 0
    private var currentY = 0
    private var pointColor: Int
    private var pointWidth: Float
    private var lineColor: Int
    private var lineWidth: Float
    private var stepValuePx = 1f
    private var stepCounter = 0

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (stepCounter * step > width) {
            stepValuePx = (width / (stepCounter * step))
        }
        pointList.forEachIndexed { index, currentDataPoint ->
            val realX = currentDataPoint.x.getNormalX()
            val realY = currentDataPoint.y.getNormalY()
            if (index < pointList.size) {
                val previousDataPoint = if (index == 0) {
                    pointList[index]
                } else {
                    pointList[index - 1]
                }
                val startX = previousDataPoint.x.getNormalX()
                val startY = previousDataPoint.y.getNormalY()
                canvas?.drawLine(startX, startY, realX, realY, dataPointLinePaint)
            }
            canvas?.drawCircle(realX, realY, pointWidth, dataPointFillPaint)
            canvas?.drawCircle(realX, realY, pointWidth, dataPointPaint)
        }
        canvas?.drawLine(
            20f,
            0f,
            20f,
            (height - 20).toFloat(),
            axisLinePaint
        )
        canvas?.drawLine(
            20f,
            (height - 20).toFloat(),
            width.toFloat(),
            (height - 20).toFloat(),
            axisLinePaint
        )
        var currentWith: Int = width
        var lastSerif = step * stepValuePx
        while (currentWith > step * stepValuePx) {
            canvas?.drawLine(
                lastSerif + 20,
                (height - 20).toFloat(),
                lastSerif + 20,
                height - serifWidth - 20,
                axisLinePaint
            )
            lastSerif += step * stepValuePx
            currentWith -= (step * stepValuePx).toInt()
        }
        var currentHeight = height
        lastSerif = step
        while (currentHeight > step) {
            canvas?.drawLine(
                20f,
                (currentHeight - 20).toFloat(),
                serifWidth + 20,
                (currentHeight - 20).toFloat(),
                axisLinePaint
            )
            lastSerif += step
            currentHeight -= (step).toInt()
        }
    }

    private fun Int.getNormalX(): Float {
        return this.toFloat() * stepValuePx + 20
    }

    private fun Int.getNormalY(): Float {
        return (height - this - 20).toFloat()
    }

    fun addPoint(y: Int) {
        stepCounter++
        if (pointList.size == 0) {
            currentX
            currentY = y
            pointList.add(DataPoint(currentX, currentY))
        } else {
            currentX += step.toInt()
            currentY = y + 20
            pointList.add(DataPoint(currentX, currentY))
        }
        invalidate()
    }

    fun setStep(step: Float) {
        this.step = step
        invalidate()
    }

    fun updatePointList(list: ArrayList<Int>){
        currentX = 0
        currentY = 0
        pointList.clear()
        stepCounter = 0
        list.map { item ->
            stepCounter ++
            if (pointList.size == 0) {
                currentX
                currentY = item
                pointList.add(DataPoint(currentX, currentY))
            } else {
                currentX += step.toInt()
                currentY = item + 20
                pointList.add(DataPoint(currentX, currentY))
            }
        }
        invalidate()
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.GraphView,
            0,
            0
        ).apply {
            pointColor = this.getColor(
                R.styleable.GraphView_pointColor,
                ContextCompat.getColor(context, R.color.deep_pink)
            )
            pointWidth = this.getFloat(
                R.styleable.GraphView_pointWidth, 5f
            )
            lineColor = this.getColor(
                R.styleable.GraphView_lineColor,
                ContextCompat.getColor(context, R.color.indigo)
            )
            lineWidth = this.getFloat(
                R.styleable.GraphView_lineWidth, 5f
            )
            dataPointPaint = Paint().apply {
                color = pointColor
                strokeWidth = pointWidth
                style = Paint.Style.STROKE
            }
            dataPointFillPaint = Paint().apply {
                color = pointColor
            }
            dataPointLinePaint = Paint().apply {
                color = lineColor
                strokeWidth = 7f
                isAntiAlias = true
            }
            axisLinePaint = Paint().apply {
                color = ContextCompat.getColor(context, R.color.gray)
                strokeWidth = lineWidth
            }
        }
    }

    companion object {
        const val serifWidth = 10f
    }
}
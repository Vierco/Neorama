package com.alvarez.sergio.actraining.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.alvarez.sergio.actraining.R
import extensions.fakeNeoId
import extensions.showSnackBar

class VisibleUniverse(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var centerX = 0f
    private var centerY = 0f
    private var scale = 1f
    private var distance = 0f
    private var positionAdjustment = 40

    private var neoFakeList = mutableListOf<String>()

    private var neosOutOfVisibleArea = false
    private var showWarningofNeosOutOfVisibleArea = true
    private var localFakeNeoList = false

    private var mapPointAssignment = mutableListOf<PointF>()

    val PAINT_NEO_SIZE = 30f
    val PAINT_NEO_SIZE_EARTH = 35f

    private val paintNeo = Paint().apply {
        color = Color.YELLOW
        style = Paint.Style.FILL
        textSize = PAINT_NEO_SIZE
        scaleX = scaleX
        scaleY = scaleY
    }

    private val paintEarth = Paint().apply {
        color = Color.CYAN
        style = Paint.Style.FILL
        textSize = PAINT_NEO_SIZE_EARTH
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        val radiusCenter = 10f
        canvas.drawCircle(centerX, centerY, 15f, paintEarth)
        canvas.drawText("Earth", centerX, centerY + 50, paintEarth)

        for ((index, point) in mapPointAssignment.withIndex()) {
            val pointX = centerX + point.x
            val pointY = centerY + point.y

            if (pointX < 0 || pointX > width || pointY < 0 || pointY > height) {
                neosOutOfVisibleArea = true
            }

            val fakeNeoId = fakeNeoId()

            canvas.drawCircle(centerX + point.x, centerY + point.y, radiusCenter, paintNeo)

            if (localFakeNeoList) {
                canvas.drawText(neoFakeList[index], pointX, pointY + positionAdjustment, paintNeo)
            } else {
                canvas.drawText(fakeNeoId, pointX, pointY + positionAdjustment, paintNeo)
            }

            neoFakeList.add(fakeNeoId)
        }

        localFakeNeoList = true

        if (neosOutOfVisibleArea && showWarningofNeosOutOfVisibleArea) {
            context.showSnackBar(context.getString(R.string.aware), context.getString(R.string.out_of_range))
            showWarningofNeosOutOfVisibleArea = false
        }
    }

    fun update(centerX: Float, centerY: Float, distance: Float, scale: Float, neos: MutableList<PointF>) {
        this.centerX = centerX
        this.centerY = centerY
        this.distance = distance
        this.scale = scale

        mapPointAssignment = neos

        invalidate()
    }

    fun makeScale(scale: Float) {
        this.scale = scale
        for (point in mapPointAssignment) {
            point.x = point.x * scale
            point.y = point.y * scale
        }
        invalidate()
    }
}

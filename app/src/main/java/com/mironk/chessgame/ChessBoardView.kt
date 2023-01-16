package com.mironk.chessgame


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class ChessBoardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private var startX = -1f
    private var startY = -1f
    private var endX = -1f
    private var endY = -1f
    private var startPos = -1
    private var endPos = -1
    private var possibleMoves = arrayListOf<Int>()
    private var knight = Knight()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw chessboard
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                if ((i + j) % 2 == 0) {
                    paint.color = Color.BLACK
                } else {
                    paint.color = Color.WHITE
                }
                canvas.drawRect(i * width / 8f, j * height / 8f, (i + 1) * width / 8f, (j + 1) * height / 8f, paint)
            }
        }

        // Draw start and end positions
        if (startX != -1f && startY != -1f) {
            paint.color = Color.RED
            canvas.drawCircle(startX, startY, width / 16f, paint)
        }
        if (endX != -1f && endY != -1f) {
            paint.color = Color.GREEN
            canvas.drawCircle(endX, endY, width / 16f, paint)
        }

        // Draw possible moves
        paint.color = Color.YELLOW
        if (startPos != -1 && endPos != -1) {
            possibleMoves = knight.getPossibleMoves(startPos, endPos)
            for (pos in possibleMoves) {
                val x = pos % 8
                val y = pos / 8
                canvas.drawCircle(x * width / 8f + width / 16f, y * height / 8f + height / 16f, width / 32f, paint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                startPos = (event.y / height * 8 + event.x / width * 8).toInt()
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                endX = event.x
                endY = event.y
                endPos = (event.y / height * 8 + event.x / width * 8).toInt()
                invalidate()
            }
        }
        return true
    }
}



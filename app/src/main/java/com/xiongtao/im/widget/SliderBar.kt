package com.xiongtao.im.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout
import androidx.core.view.size
import com.xiongtao.im.R
import org.jetbrains.anko.sp

@SuppressLint("ResourceAsColor")
class SliderBar(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    var sectionHeight = 0
    var baseLine = 0f
    companion object {
        val LETTERS = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    }

    lateinit var onTouchListener: OnSliderTouchListener

    val paint by lazy {
        Paint()
    }
    init {
        paint.apply {
            color = R.color.purple_200
            isAntiAlias = true
            textSize = sp(16).toFloat()
            textAlign = Paint.Align.CENTER
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        sectionHeight = h / LETTERS.size

        val fontMetrics = paint.fontMetrics
        val textHeight = fontMetrics.descent - fontMetrics.ascent
        baseLine = sectionHeight / 2 +(textHeight/2 - fontMetrics.descent)

    }

    override fun onDraw(canvas: Canvas) {
        var x = width/2
        var y = baseLine
        LETTERS.forEachIndexed { index, s ->
            canvas.drawText(s,x.toFloat(),y.toFloat(),paint)
            y += sectionHeight
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                setBackgroundResource(R.drawable.contact_bg)
                var index = (event.y / sectionHeight).toInt()
                if(index < 0) index = 0
                if(index >= LETTERS.size) index = LETTERS.size -1
                val char = LETTERS[index]
                onTouchListener.onShowChar(char,index)
            }
            MotionEvent.ACTION_UP -> {
                setBackgroundColor(Color.TRANSPARENT)
                onTouchListener.onHideChar()
            }
            MotionEvent.ACTION_MOVE -> {
                var index = (event.y / sectionHeight).toInt()
                if(index < 0) index = 0
                if(index >= LETTERS.size) index = LETTERS.size -1
                val char = LETTERS[index]
                onTouchListener.onShowChar(char,index)
            }
        }

        return true

    }

}
interface OnSliderTouchListener{
    fun onShowChar(string: String,index:Int)
    fun onHideChar()
}
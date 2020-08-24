package com.example.camera

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap

class Mask(ctx: Context, attrs: AttributeSet) : AppCompatImageView(ctx, attrs) {

    private var bitmapFront: Bitmap? = null
    private var bitmapStroke: Bitmap? = null
    private val mode = PorterDuffXfermode(PorterDuff.Mode.XOR)
    private var strokeWidth:Int = 0
    private val paint = Paint().apply {
        style = Paint.Style.FILL
    }

    init {
        val attributes = ctx.obtainStyledAttributes(attrs, R.styleable.Mask)
        this.bitmapFront = attributes.getDrawable(0)?.toBitmap()
        this.strokeWidth = attributes.getDimension(R.styleable.Mask_strokeWidth, 0f).toInt()
        this.bitmapFront?.let {
            this.bitmapStroke = Bitmap.createScaledBitmap(it, it.width + this.strokeWidth, it.height + this.strokeWidth, false)
        }
        attributes.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        this.bitmapFront?.let {
            canvas?.drawBitmap(
                this.bitmapStroke!!,
                (width - this.bitmapStroke!!.width) / 2f,
                (height - this.bitmapStroke!!.height) / 2f,
                this.paint
            )
            this.paint.xfermode = this.mode
            canvas?.drawBitmap(it, (width - it.width) / 2f, (height - it.height) / 2f, this.paint)
        }
    }
}
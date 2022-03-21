package com.wenote.compleximage

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.cray.bannerimageview.R


@SuppressLint("AppCompatCustomView")
class ComplexImage : ImageView {

    open var frontText: String? = null;
    open var frontTextColor: Int = -1;
    open lateinit var typedArray: TypedArray;

    var paint: Paint? = null

    var frontTextSize: Float? = -1f;
    var imageWidth: Int = 0;
    var imageHeight: Int = 0;

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        getAttrs(context, attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        getAttrs(context, attrs)
    }

    init {
        paint = Paint()
    }

    private fun getAttrs(context: Context?, attrs: AttributeSet?) {
        typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.ComplexImage)

        if (typedArray == null) return

        frontText = typedArray?.getString(R.styleable.ComplexImage_frontText).toString()

        frontTextColor = typedArray?.getColor(R.styleable.ComplexImage_frontTextColor, Color.BLACK)

        frontTextSize = typedArray?.getDimension(R.styleable.ComplexImage_textSize, -1f)
        if (frontTextSize == -1f) {
            frontTextSize = getTextSize(defaultTextSize())
        }
    }

    // 默认的字体大小
    private fun defaultTextSize(): Float {
        return DensityUtils.sp2px(context, 16.0.toFloat())
    }

    // 根据xml的sp，转换成px
    private fun getTextSize(textSpSize: Float): Float {
        return DensityUtils.sp2px(context, textSpSize)
    }

    @SuppressLint("ResourceAsColor")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //设置背景色

        val canvasWidth: Int = canvas!!.width
        val canvasHeight: Int = canvas!!.height

        canvas!!.saveLayer(0f, 0f, canvasWidth.toFloat(), canvasHeight.toFloat(), null, Canvas.ALL_SAVE_FLAG)

        val rect = Rect(0, 0, imageWidth, imageHeight)
        paint?.setColor(Color.GRAY)
        paint?.alpha = 150
        paint?.isAntiAlias = true
        canvas.drawRect(rect, paint!!)

        canvas!!.saveLayer(0f, 0f, canvasWidth.toFloat(), canvasHeight.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        paint?.setColor(frontTextColor)
        paint?.textSize = frontTextSize!!

        var xOffSet = (imageWidth - frontText!!.length * frontTextSize!!) / 2.toFloat()
        var yOffSet = (imageHeight + frontTextSize!!) / 2.toFloat()

        canvas.drawText(frontText!!, xOffSet, yOffSet, paint!!)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        imageHeight = h
        imageWidth = w
    }
}
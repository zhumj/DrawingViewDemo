package com.example.drawingviewdemo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.drawingviewdemo.R
import kotlin.math.abs

class DrawingView: View {

    constructor(context: Context?): this(context, null)

    constructor(context: Context?, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context?, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    companion object {
        const val DEFAULT_BG_COLOR = 0 // 默认背景色
        const val DEFAULT_COLOR = R.color.drawColor1 // 默认画笔颜色
        const val HIGHLIGHTER_TRANSPARENCY = 100 // 默认荧光笔透明度
        const val PENCIL_OPAQUE = 255 // 默认画笔不透明
        val BRUSH_SIZE_ARRAY = arrayOf(12, 20, 35, 55) // 画笔宽度集合
    }

    var bgColor: Int = DEFAULT_BG_COLOR // 背景色
    var enableDrawing: Boolean = true // 是否启用画图
    var currentMode: MODE = MODE.PENCIL // 画笔类型
    var strokeWidth: Int = BRUSH_SIZE_ARRAY[0] // 画笔宽度
    var currentColor: Int = DEFAULT_COLOR // 画笔颜色

    private var mBitmap: Bitmap? = null
    private var mBitmapPaint: Paint = Paint(4)
    private var mCanvas: Canvas? = null

    private var mOldBitmap: Bitmap? = null
    private var mPaint: Paint = Paint()
    private var mPath: Path? = null

    private var mX: Float = 0f
    private var mY: Float = 0f

    private var paths: ArrayList<FingerPath> = ArrayList()

    enum class MODE(val value: Int) {
        PENCIL(0), // 铅笔
        HIGHLIGHTER(1), // 荧光笔
        ERASER(2); // 橡皮擦
    }

    init {
        mPaint.isAntiAlias = true // 是否使用抗锯齿功能
        mPaint.isDither = true // 是否使用图像抖动处理
        mPaint.color = ContextCompat.getColor(context!!, DEFAULT_COLOR)
        mPaint.style = Paint.Style.STROKE // 画笔的样式，为FILL，FILL_OR_STROKE，或STROKE
        mPaint.strokeJoin = Paint.Join.ROUND // 设置绘制时各图形的结合方式，如平滑效果等
        mPaint.strokeCap = Paint.Cap.ROUND // 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式 Cap.ROUND,或方形样式Cap.SQUARE
        mPaint.xfermode = null // 图形重叠时的处理方式，如合并，取交集或并集，经常用来制作橡皮的擦除效果
        mPaint.alpha = PENCIL_OPAQUE
    }

    fun initBitmap(displayMetrics: DisplayMetrics) {
        this.mBitmap = Bitmap.createBitmap(displayMetrics.widthPixels, displayMetrics.heightPixels, Bitmap.Config.ARGB_8888);
        this.mCanvas = Canvas(this.mBitmap!!)
    }

    fun undoDrawing() {
        if (this.paths.size > 0) {
            removeIncompletePath()
        }
        invalidate()
    }

    private fun removeIncompletePath() {
        if (!this.paths.last().pathComplete) {
            this.paths.remove(this.paths.last())
            if (this.paths.size > 0) {
                removeIncompletePath()
                return
            }
            return
        }
        this.paths.remove(this.paths.last())
    }


    fun clear() {
        this.bgColor = DEFAULT_BG_COLOR
        this.paths.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return
        canvas.save()
        this.mCanvas?.drawColor(this.bgColor, PorterDuff.Mode.CLEAR);
        if (this.mOldBitmap != null) {
            this.mCanvas?.drawBitmap(this.mOldBitmap!!, 0.0f, 0.0f, this.mBitmapPaint)
        }
        val it = this.paths.iterator()
        while (it.hasNext()) {
            val next = it.next()
            mPaint.color = ContextCompat.getColor(context, next.color)
            mPaint.strokeWidth = next.strokeWidth.toFloat()
            mPaint.maskFilter = null
            if (next.drawingMode == MODE.ERASER) {
                this.mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            } else {
                this.mPaint.xfermode = null
            }
            if (next.drawingMode == MODE.HIGHLIGHTER) {
                this.mPaint.alpha = HIGHLIGHTER_TRANSPARENCY
            }
//            else {
//                val alpha = this.mPaint.alpha
//                val i = PENCIL_OPAQUE
//            }
            this.mCanvas?.drawPath(next.path, this.mPaint)
        }
        canvas.drawBitmap(this.mBitmap!!, 0f, 0f, this.mBitmapPaint)
        canvas.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!this.enableDrawing) {
            return false
        }
        val x = event.x
        val y = event.y
        when (event.action) {
            0 -> {
                touchStart(x, y)
                invalidate()
                return true
            }
            1 -> {
                touchUp()
                invalidate()
                return true
            }
            2 -> {
                touchMove(x, y)
                invalidate()
                return true
            }
            else -> {
                return true
            }
        }
    }

    private fun touchStart(x: Float, y: Float) {
        this.mPath = Path()
        val i = this.currentColor
        val i2 = this.strokeWidth
        this.paths.add(FingerPath(i, i2, this.mPath!!, this.currentMode, false))
        this.mPath?.reset()
        this.mPath?.moveTo(x, y)
        this.mX = x
        this.mY = y
    }

    private fun touchUp() {
        this.paths.last().pathComplete = true
        this.mPath?.lineTo(this.mX, this.mY)
    }

    private fun touchMove(x: Float, y: Float) {
        val abs = abs(x - this.mX)
        val abs2 = abs(y - this.mY)
        if (abs >= strokeWidth || abs2 >= strokeWidth) {
            val f3 = 2f
            this.mPath?.quadTo(this.mX, this.mY, (this.mX + x) / f3, (this.mY + y) / f3)
            this.mX = x
            this.mY = y
        }
    }

}
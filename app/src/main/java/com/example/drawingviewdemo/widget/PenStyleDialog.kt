package com.example.drawingviewdemo.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.ColorRes
import com.example.drawingviewdemo.R
import kotlinx.android.synthetic.main.dialog_pen_style.*

class PenStyleDialog(context: Context) : Dialog(context) {

    private var listener: OnDialogValueChangeListener? = null

    private var thickness: Int = 12
    private var colorId: Int = R.color.drawColor1
    private var mode: DrawingView.MODE = DrawingView.MODE.PENCIL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_pen_style)

        rgThickness.setOnCheckedChangeListener { _, checkedId ->
            thickness = when (checkedId) {
                R.id.rbThickness12 -> 12
                R.id.rbThickness20 -> 20
                R.id.rbThickness35 -> 35
                else -> 55
            }
            listener?.onValueChange(thickness, colorId, mode)
        }

        rgColor.setOnCheckedChangeListener(object: MultiLineRadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: MultiLineRadioGroup?, checkedId: Int) {
                colorId = when (checkedId) {
                    R.id.rbColor1 -> R.color.drawColor1
                    R.id.rbColor2 -> R.color.drawColor2
                    R.id.rbColor3 -> R.color.drawColor3
                    R.id.rbColor4 -> R.color.drawColor4
                    R.id.rbColor5 -> R.color.drawColor5
                    R.id.rbColor6 -> R.color.drawColor6
                    R.id.rbColor7 -> R.color.drawColor7
                    R.id.rbColor8 -> R.color.drawColor8
                    R.id.rbColor9 -> R.color.drawColor9
                    R.id.rbColor10 -> R.color.drawColor10
                    R.id.rbColor11 -> R.color.drawColor11
                    R.id.rbColor12 -> R.color.drawColor12
                    R.id.rbColor13 -> R.color.drawColor13
                    else -> R.color.drawColor14
                }
                listener?.onValueChange(thickness, colorId, mode)
            }
        })

        rgTool.setOnCheckedChangeListener { _, checkedId ->
            mode = when (checkedId) {
                R.id.rbTool1 -> DrawingView.MODE.PENCIL
                R.id.rbTool2 -> DrawingView.MODE.HIGHLIGHTER
                else ->  DrawingView.MODE.ERASER
            }
            listener?.onValueChange(thickness, colorId, mode)
        }
    }

    fun setValue(thickness: Int, @ColorRes colorId: Int, mode: DrawingView.MODE) {
        setThickness(thickness)
        setColor(colorId)
        setPenMode(mode)
    }

    fun setThickness(thickness: Int) {
        this.thickness = thickness
        when (thickness) {
            12 -> rgThickness.check(R.id.rbThickness12)
            20 -> rgThickness.check(R.id.rbThickness20)
            35 -> rgThickness.check(R.id.rbThickness35)
            else -> rgThickness.check(R.id.rbThickness55)
        }
    }

    fun setColor(@ColorRes colorId: Int) {
        this.colorId = colorId
        when (colorId) {
            R.color.drawColor1 -> rgColor.check(R.id.rbColor1)
            R.color.drawColor2 -> rgColor.check(R.id.rbColor2)
            R.color.drawColor3 -> rgColor.check(R.id.rbColor3)
            R.color.drawColor4 -> rgColor.check(R.id.rbColor4)
            R.color.drawColor5 -> rgColor.check(R.id.rbColor5)
            R.color.drawColor6 -> rgColor.check(R.id.rbColor6)
            R.color.drawColor7 -> rgColor.check(R.id.rbColor7)
            R.color.drawColor8 -> rgColor.check(R.id.rbColor8)
            R.color.drawColor9 -> rgColor.check(R.id.rbColor9)
            R.color.drawColor10 -> rgColor.check(R.id.rbColor10)
            R.color.drawColor11 -> rgColor.check(R.id.rbColor11)
            R.color.drawColor12 -> rgColor.check(R.id.rbColor12)
            R.color.drawColor13 -> rgColor.check(R.id.rbColor13)
            else -> rgColor.check(R.id.rbColor14)
        }
    }

    fun setPenMode(mode: DrawingView.MODE) {
        this.mode = mode
        when (mode) {
            DrawingView.MODE.PENCIL -> rgTool.check(R.id.rbTool1)
            DrawingView.MODE.HIGHLIGHTER -> rgTool.check(R.id.rbTool2)
            else -> rgTool.check(R.id.rbTool3)
        }
    }

    fun setOnDialogValueChangeListener(listener: OnDialogValueChangeListener) {
        this.listener = listener
    }

    interface OnDialogValueChangeListener{
        fun onValueChange(thickness: Int, colorId: Int, mode: DrawingView.MODE)
    }

}
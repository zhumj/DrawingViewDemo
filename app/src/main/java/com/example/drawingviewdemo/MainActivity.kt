package com.example.drawingviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import com.example.drawingviewdemo.widget.DrawingView
import com.example.drawingviewdemo.widget.PenStyleDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDrawingVisible(true)

        val displayMetrics = DisplayMetrics()
        val windowManager = windowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        drawingView.initBitmap(displayMetrics)
    }

    fun onUndoClick(view: View) {
        drawingView.undoDrawing()
    }

    fun onStyleClick(view: View) {
        val dialog = PenStyleDialog(this)
        dialog.show()
        dialog.setValue(drawingView.strokeWidth, drawingView.currentColor, drawingView.currentMode)
        dialog.setOnDialogValueChangeListener(object : PenStyleDialog.OnDialogValueChangeListener {
            override fun onValueChange(thickness: Int, colorId: Int, mode: DrawingView.MODE) {
                this@MainActivity.drawingView.currentColor = colorId
                this@MainActivity.drawingView.currentMode = mode
                this@MainActivity.drawingView.strokeWidth = thickness
            }
        })
    }

    fun onClearClick(view: View) {
        drawingView.clear()
    }

    fun onSaveClick(view: View) {
        if (drawingView.mBitmap == null) {
            ToastHelper.showToast(this@MainActivity, "Bitmap 为 null")
            return
        }
        setDrawingVisible(false)
        ivDrawImage.setImageBitmap(drawingView.mBitmap)
    }

    fun onBackClick(view: View) {
        setDrawingVisible(true)
    }

    private fun setDrawingVisible(drawingVisible: Boolean) {
        drawingView.visibility = if (drawingVisible) View.VISIBLE else View.GONE
        llBottom.visibility = drawingView.visibility
        llShowDrawImage.visibility = if (drawingVisible) View.GONE else View.VISIBLE
    }
}

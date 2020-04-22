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
}

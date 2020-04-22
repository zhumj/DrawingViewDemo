package com.example.drawingviewdemo.widget

import android.graphics.Path
import com.example.drawingviewdemo.widget.DrawingView.MODE

class FingerPath(
    i: Int,
    i2: Int,
    path2: Path,
    mode: MODE,
    z: Boolean
) {
    var color: Int = i
    var drawingMode: MODE = mode
    var path: Path = path2
    var pathComplete: Boolean = z
    var strokeWidth: Int = i2
}
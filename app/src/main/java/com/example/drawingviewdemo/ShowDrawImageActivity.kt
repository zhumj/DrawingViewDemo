package com.example.drawingviewdemo

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_show_draw_image.*

class ShowDrawImageActivity : AppCompatActivity() {

    companion object {
        val DRAW_BITMAP = "draw_bitmap"
        fun startShowDrawImageActivity(context: Context, bitmap: Bitmap?) {
            val intent = Intent(context, ShowDrawImageActivity::class.java)
            intent.putExtra(DRAW_BITMAP, bitmap)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_draw_image)

        val bitmap = intent.getParcelableExtra<Bitmap>(DRAW_BITMAP)
        if (bitmap != null) {
            ivDrawImage.setImageBitmap(bitmap)
        }
    }
}

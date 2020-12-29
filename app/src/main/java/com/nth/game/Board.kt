package com.nth.game

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import java.util.*

/**
 * Created by NguyenTienHoa on 12/28/2020
 */

class Board {


//    var background: Bitmap

    var row = 10
    var col = 10
    val space = 4
    var weight : Float
    var boardW : Float
    var boardH : Float
    val listCandy:MutableList<Candy> = mutableListOf()
    var candy:CandyBits
    var screenW: Float
    var screenH : Float


    constructor(screenW: Float, screenH: Float, res: Resources){
        this.screenW = screenW
        this.screenH = screenH
        boardW = screenW - 200 + space*col
        weight = boardW / col
        boardH = weight * row + space*row
        candy = CandyBits(weight,res)

//        background = BitmapFactory.decodeResource(res, R.drawable.bg_play)
//        background = Bitmap.createScaledBitmap(background, screenX.toInt(), screenY.toInt(), false)
    }

    fun draw(canvas: Canvas, paint: Paint){
        val x = screenW / 2 - boardW/2
        val y = screenH / 2 - boardH/2

        paint.color = Color.argb(111, 20, 180, 255)
        for (i in 0 until col) {
            for (j in 0 until row) {
                val tempX = x + i * weight + space
                val tempY = y + j * weight + space
                canvas.drawRect(tempX, tempY, x + weight + i * weight, y + weight + j * weight, paint)
                val index = Random().nextInt(candy.getCandy().size)
                paint.color = Color.BLUE
                canvas.drawBitmap(candy.getCandy()[index],tempX,tempY,paint)
                paint.color = Color.argb(111, 20, 180, 255)
            }
        }
    }

}
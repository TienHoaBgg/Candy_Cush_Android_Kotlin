package com.nth.game.model

import android.graphics.Bitmap
import android.graphics.Point

data class Candy(var x: Float, var y: Float, var width: Int, var candy:CandyBit,var isSelect: Boolean, var isMatch:Boolean) {
    val point: Point = Point((x + width / 2).toInt(), (y + width / 2).toInt())
}

package com.nth.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.util.*


/**
 * Created by NguyenTienHoa on 12/30/2020
 */

class CandyBits {

    var speed = 500
    var wasShot = true
    var x = 0
    var y = 0
    var weight:Int
    var height:Int = 0
    var birdCounter:Int = 1
    var candy1: Bitmap? = null
    var candy2: Bitmap? = null
    var candy3: Bitmap? = null
    var candy4: Bitmap? = null
    var candy5: Bitmap? = null
    var candy6: Bitmap? = null

    val list:MutableList<Bitmap>
    constructor(w:Float,res: Resources){
        candy1 = BitmapFactory.decodeResource(res, R.drawable.c_blue)
        candy2 = BitmapFactory.decodeResource(res, R.drawable.c_green)
        candy3 = BitmapFactory.decodeResource(res, R.drawable.c_orange)
        candy4 = BitmapFactory.decodeResource(res, R.drawable.c_purple)
        candy5 = BitmapFactory.decodeResource(res, R.drawable.c_red)
        candy6 = BitmapFactory.decodeResource(res, R.drawable.c_yellow)

        this.weight = w.toInt() - 10

        candy1 = Bitmap.createScaledBitmap(candy1!!, weight, weight, false)
        candy2 = Bitmap.createScaledBitmap(candy2!!, weight, weight, false)
        candy3 = Bitmap.createScaledBitmap(candy3!!, weight, weight, false)
        candy4 = Bitmap.createScaledBitmap(candy4!!, weight, weight, false)
        candy5 = Bitmap.createScaledBitmap(candy5!!, weight, weight, false)
        candy6 = Bitmap.createScaledBitmap(candy6!!, weight, weight, false)
        list = mutableListOf()
        list.add(candy1!!)
        list.add(candy2!!)
        list.add(candy3!!)
        list.add(candy4!!)
        list.add(candy5!!)
        list.add(candy6!!)
    }

    fun getCandy(): MutableList<Bitmap> {
//       val index = Random().nextInt(list.size)
        return list
    }

    fun getCollisionShape(): Rect {
        return Rect(x, y, x + weight, y + height)
    }

}
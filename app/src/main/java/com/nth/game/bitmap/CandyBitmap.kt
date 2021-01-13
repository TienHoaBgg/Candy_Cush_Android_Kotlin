package com.nth.game.bitmap

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.nth.game.Constant
import com.nth.game.R
import com.nth.game.model.CandyBit
import java.util.*


/**
 * Created by NguyenTienHoa on 12/30/2020
 */

class CandyBitmap {

    var width:Int = 0
    var candy1: Bitmap? = null
    var candy2: Bitmap? = null
    var candy3: Bitmap? = null
    var candy4: Bitmap? = null
    var candy5: Bitmap? = null
    var candy6: Bitmap? = null
    var rock:Bitmap? = null

    private val list:MutableList<CandyBit>

    constructor(w:Int,res: Resources){
        candy1 = BitmapFactory.decodeResource(res, R.drawable.c_blue)
        candy2 = BitmapFactory.decodeResource(res, R.drawable.c_green)
        candy3 = BitmapFactory.decodeResource(res, R.drawable.c_orange)
        candy4 = BitmapFactory.decodeResource(res, R.drawable.c_purple)
        candy5 = BitmapFactory.decodeResource(res, R.drawable.c_red)
        candy6 = BitmapFactory.decodeResource(res, R.drawable.c_yellow)
        rock = BitmapFactory.decodeResource(res, R.drawable.rock)

        this.width = w - Constant.spaceCandy

        candy1 = Bitmap.createScaledBitmap(candy1!!, width, width, false)
        candy2 = Bitmap.createScaledBitmap(candy2!!, width, width, false)
        candy3 = Bitmap.createScaledBitmap(candy3!!, width, width, false)
        candy4 = Bitmap.createScaledBitmap(candy4!!, width, width, false)
        candy5 = Bitmap.createScaledBitmap(candy5!!, width, width, false)
        candy6 = Bitmap.createScaledBitmap(candy6!!, width, width, false)
        rock = Bitmap.createScaledBitmap(rock!!, width, width, false)

        list = mutableListOf()
        list.add(CandyBit(1,candy1!!))
        list.add(CandyBit(2,candy2!!))
        list.add(CandyBit(3,candy3!!))
        list.add(CandyBit(4,candy4!!))
        list.add(CandyBit(5,candy5!!))
        list.add(CandyBit(6,candy6!!))
    }

    fun getCandy(): CandyBit {
       val index = Random().nextInt(list.size)
        return list[index]
    }

    fun getRocks(): Bitmap {
        return rock!!
    }

}
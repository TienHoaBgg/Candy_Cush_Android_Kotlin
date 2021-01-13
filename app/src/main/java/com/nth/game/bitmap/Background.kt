package com.nth.game.bitmap

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.nth.game.R
import java.util.*

/**
 * Created by NguyenTienHoa on 12/23/2020
 */

class Background {

    var background: Bitmap
    private var background1: Bitmap
    private var background2: Bitmap
    private var background3: Bitmap
    private var background4: Bitmap
    private var background5: Bitmap
    private var background6: Bitmap
    var x:Float = 0f
    var y:Float = 0f
    private var listBG:MutableList<Bitmap>

    constructor(screenX: Float, screenY: Float, res: Resources){
        background = BitmapFactory.decodeResource(res, R.drawable.bg_play)
        background1 = BitmapFactory.decodeResource(res, R.drawable.bg1)
        background2 = BitmapFactory.decodeResource(res, R.drawable.bg2)
        background3 = BitmapFactory.decodeResource(res, R.drawable.bg3)
        background4 = BitmapFactory.decodeResource(res, R.drawable.bg4)
        background5 = BitmapFactory.decodeResource(res, R.drawable.bg5)
        background6 = BitmapFactory.decodeResource(res, R.drawable.bg6)

        listBG = arrayListOf()

        background = Bitmap.createScaledBitmap(background, screenX.toInt(), screenY.toInt(), false)
        background1 = Bitmap.createScaledBitmap(background1, screenX.toInt(), screenY.toInt(), false)
        background2 = Bitmap.createScaledBitmap(background2, screenX.toInt(), screenY.toInt(), false)
        background3 = Bitmap.createScaledBitmap(background3, screenX.toInt(), screenY.toInt(), false)
        background4 = Bitmap.createScaledBitmap(background4, screenX.toInt(), screenY.toInt(), false)
        background5 = Bitmap.createScaledBitmap(background5, screenX.toInt(), screenY.toInt(), false)
        background6 = Bitmap.createScaledBitmap(background6, screenX.toInt(), screenY.toInt(), false)

        listBG.add(background)
        listBG.add(background1)
        listBG.add(background2)
        listBG.add(background3)
        listBG.add(background4)
        listBG.add(background5)
        listBG.add(background6)

    }

    fun getBackgroundRd():Bitmap{
        val index = Random().nextInt(listBG.size)
        return listBG[index]
    }

}
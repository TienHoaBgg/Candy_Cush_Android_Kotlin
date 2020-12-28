package com.nth.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * Created by NguyenTienHoa on 12/23/2020
 */

class Background {

    var background: Bitmap
    var x:Float = 0f
    var y:Float = 0f
    constructor(screenX: Float, screenY: Float, res: Resources){
        background = BitmapFactory.decodeResource(res, R.drawable.bg_play)
        background = Bitmap.createScaledBitmap(background, screenX.toInt(), screenY.toInt(), false)
    }


}
package com.nth.game.bitmap

import android.content.res.Resources
import android.graphics.*
import com.nth.game.Constant
import com.nth.game.R
import com.nth.game.model.MBitmap

/**
 * Created by NguyenTienHoa on 1/10/2021
 */

class Footer {

    var btnHome: MBitmap = MBitmap()
    var btnReload: MBitmap = MBitmap()

    constructor(res: Resources){
        btnHome.bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_home)
        btnReload.bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_reload)

        btnHome.x = (Constant.screenW/2 - Constant.footerH)
        btnHome.y = Constant.screenH - Constant.footerH + 10
        btnHome.width = Constant.footerH.toInt() - 20

        btnReload.x = (Constant.screenW/2 + 10).toFloat()
        btnReload.y = Constant.screenH - Constant.footerH + 10
        btnReload.width = Constant.footerH.toInt() - 20

        btnHome.bitmap = Bitmap.createScaledBitmap(btnHome.bitmap!!, btnHome.width, btnHome.width, false)
        btnReload.bitmap = Bitmap.createScaledBitmap( btnReload.bitmap!!, btnReload.width, btnReload.width, false)
    }

    fun draw(canvas: Canvas, paint: Paint){
        paint.color = Color.argb(60,238,0,238)
        canvas.drawRect(
                0f, Constant.screenH - Constant.footerH,
                Constant.screenW.toFloat(),
                Constant.screenH.toFloat(),
                paint
        )
        paint.color = Color.BLUE
        canvas.drawBitmap(btnHome.bitmap!!,btnHome.x,btnHome.y,paint)
        canvas.drawBitmap(btnReload.bitmap!!,btnReload.x,btnReload.y,paint)
    }



}
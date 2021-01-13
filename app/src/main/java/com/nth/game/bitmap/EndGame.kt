package com.nth.game.bitmap

import android.content.res.Resources
import android.graphics.*
import com.nth.game.Constant
import com.nth.game.R
import com.nth.game.model.Level
import com.nth.game.model.MBitmap

/**
 * Created by NguyenTienHoa on 1/11/2021
 */

class EndGame {

    var btnClose: MBitmap = MBitmap()
    var btnHome: MBitmap = MBitmap()
    var btnReload: MBitmap = MBitmap()
    var btnNext: MBitmap = MBitmap()
    private var endGame0:Bitmap
    private var endGame1:Bitmap
    private var endGame2:Bitmap
    private var endGame3:Bitmap

    var x = 0f
    var y = 0f
    var width = 0
    var height = 0
    var level = ""
    private var lx = 0f
    private var ly = 0f
    private var scoreX = 0f
    private var scoreY = 0f

    var tempY = 0f
    var isDone = false
    var isOut = false
    var isDraw = true

    constructor(res: Resources){

        endGame0 = BitmapFactory.decodeResource(res, R.drawable.end_game0)
        endGame1 = BitmapFactory.decodeResource(res, R.drawable.end_game1)
        endGame2 = BitmapFactory.decodeResource(res, R.drawable.end_game2)
        endGame3 = BitmapFactory.decodeResource(res, R.drawable.end_game3)

        width = (Constant.screenW/5)*4

        height = Constant.screenW*5/6

        tempY = (-height).toFloat()

        x = Constant.screenW/2f - width/2
        y = Constant.screenH/2f - height/2

        lx = x + (x + width)/3
        ly = y + height/11

        scoreX = lx - 8
        scoreY = y + height*8/12 - 8

        btnHome.x = lx - height/24
        btnHome.y = y + height*9.5f/12 - 8
        btnHome.width = Constant.footerH.toInt() - 20

        btnReload.x = lx + btnHome.width
        btnReload.y =  y + height*9.5f/12 - 8
        btnReload.width = Constant.footerH.toInt() - 20

        btnNext.x = btnReload.x  + btnReload.width*4/3
        btnNext.y = y + height*9.5f/12 - 8
        btnNext.width = Constant.footerH.toInt() - 20


        btnClose.width = Constant.footerH.toInt() - 50
        btnClose.x = x + width - btnClose.width*8/7
        btnClose.y = y + btnClose.width/3


        endGame0 = Bitmap.createScaledBitmap(endGame0, width, height, false)
        endGame1 = Bitmap.createScaledBitmap( endGame1, width, height, false)
        endGame2 = Bitmap.createScaledBitmap( endGame2, width, height, false)
        endGame3 = Bitmap.createScaledBitmap( endGame3, width, height, false)

    }


    fun draw(level: Level,canvas:Canvas,paint: Paint){
        paint.color = Color.WHITE
        var bitmap:Bitmap? = null
        when(level.typeView){
            0-> bitmap = endGame0
            1-> bitmap = endGame1
            2-> bitmap = endGame2
            3-> bitmap = endGame3
        }

        if (isOut){
            if (tempY > -height){
                canvas.drawBitmap(bitmap!!,x,tempY,paint)
                tempY -= 50
            }else{
                isDone = true
            }
        }else{
            if (tempY < y){
                canvas.drawBitmap(bitmap!!,x,tempY,paint)
                tempY += 100
            }else{
                paint.textSize = 60f
                canvas.drawBitmap(bitmap!!,x,y,paint)
                canvas.drawText(level.name,lx,ly,paint)
                canvas.drawText("${level.score}",scoreX,scoreY,paint)
                isDone = true
            }
        }
    }
}
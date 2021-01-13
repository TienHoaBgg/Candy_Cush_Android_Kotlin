package com.nth.game.bitmap

import android.content.res.Resources
import android.graphics.*
import com.nth.game.Constant
import com.nth.game.R
import com.nth.game.manager.SoundManager
import com.nth.game.model.MBitmap

/**
 * Created by NguyenTienHoa on 1/9/2021
 */

class Header {

    private var background0:Bitmap
    private var background1:Bitmap
    private var background2:Bitmap
    private var background3:Bitmap
    private var background4:Bitmap
    var soundOn:MBitmap
    private var soundOff:Bitmap
    var musicOn:MBitmap
    private var musicOff:Bitmap
    private var scoreTarget:IntArray
    private var scoreRate = 0f
    private var typeTemp = 0

    var score:Long
    var moveLimit:Int
    var isSoundOff = false
    var isMusicOff = false
    var typeView = 0
    var levelName:String = ""

    constructor(scoreTarget:IntArray, res:Resources){
        this.scoreTarget = scoreTarget
        score = 0
        soundOn = MBitmap()
        musicOn = MBitmap()

        soundOn.width = (Constant.headerH / 3 - 20).toInt()
        musicOn.width = (Constant.headerH / 3 - 20).toInt()

        background0 = BitmapFactory.decodeResource(res, R.drawable.bg_header_0)
        background1 = BitmapFactory.decodeResource(res, R.drawable.bg_header_1)
        background2 = BitmapFactory.decodeResource(res, R.drawable.bg_header_2)
        background3 = BitmapFactory.decodeResource(res, R.drawable.bg_header_3)
        background4 = BitmapFactory.decodeResource(res, R.drawable.bg_header_4)
        soundOn.bitmap = BitmapFactory.decodeResource(res, R.drawable.sound_on)
        soundOff = BitmapFactory.decodeResource(res, R.drawable.sound_off)
        musicOn.bitmap = BitmapFactory.decodeResource(res, R.drawable.music_on)
        musicOff = BitmapFactory.decodeResource(res, R.drawable.music_off)

        background0 = Bitmap.createScaledBitmap(background0, Constant.screenW, Constant.headerH.toInt(), false)
        background1 = Bitmap.createScaledBitmap(background1, Constant.screenW, Constant.headerH.toInt(), false)
        background2 = Bitmap.createScaledBitmap(background2, Constant.screenW, Constant.headerH.toInt(), false)
        background3 = Bitmap.createScaledBitmap(background3, Constant.screenW, Constant.headerH.toInt(), false)
        background4 = Bitmap.createScaledBitmap(background4, Constant.screenW, Constant.headerH.toInt(), false)
        soundOn.bitmap = Bitmap.createScaledBitmap(soundOn.bitmap!!, soundOn.width,soundOn.width, false)
        soundOff = Bitmap.createScaledBitmap(soundOff,soundOn.width,soundOn.width, false)
        musicOn.bitmap = Bitmap.createScaledBitmap(musicOn.bitmap!!, musicOn.width,musicOn.width, false)
        musicOff = Bitmap.createScaledBitmap(musicOff, musicOn.width,musicOn.width, false)
        moveLimit = 0

        soundOn.x = (Constant.screenW - 100).toFloat()
        soundOn.y = 6f

        musicOn.x = (Constant.screenW - 110 -  Constant.headerH / 3)
        musicOn.y = 6f

    }

    fun draw(canvas: Canvas, paint: Paint){
        checkRateScore()
        when(scoreRate){
             in 0f..24f -> {
                 typeView = 0
                 canvas.drawBitmap(background0, 0f, 0f,paint)
            }
            in 24f..49f -> {
                typeView = 0
                canvas.drawBitmap(background1, 0f, 0f,paint)
            }
            in 49f..74f -> {
                typeView = 1
                if (typeTemp != typeView){
                    typeTemp = 1
                    SoundManager.getInstance().star1()
                }
                canvas.drawBitmap(background2, 0f, 0f,paint)
            }
            in 75f..98f -> {
                typeView = 2
                if (typeTemp != typeView){
                    typeTemp = 2
                    SoundManager.getInstance().star1()
                }
                canvas.drawBitmap(background3, 0f, 0f,paint)
            }
            else -> {
                typeView = 3
                if (typeTemp != typeView){
                    typeTemp = 3
                    SoundManager.getInstance().star1()
                }
                canvas.drawBitmap(background4, 0f, 0f,paint)
            }
        }

        if (!isSoundOff){
            canvas.drawBitmap(soundOn.bitmap!!,soundOn.x,soundOn.y,paint)
        }else{
            canvas.drawBitmap(soundOff,soundOn.x,soundOn.y,paint)
        }
        if (!isMusicOff){
            canvas.drawBitmap(musicOn.bitmap!!,musicOn.x,musicOn.y,paint)
        }else{
            canvas.drawBitmap(musicOff,musicOn.x,musicOn.y,paint)
        }
        paint.textSize = 50f
        paint.color = Color.WHITE
        canvas.drawText(levelName,(Constant.screenW /2 - 89).toFloat(),Constant.headerH/ 3,paint)

        paint.textSize = 90f
        paint.color = Color.argb(255,30,144,255)
        canvas.drawText("$score",(Constant.screenW *2/ 3 - 10).toFloat(),Constant.headerH * 2/ 3,paint)
        paint.color = Color.argb(255,238,5,5)
        var limitString = "$moveLimit"
        if (moveLimit < 10){
            limitString = "0$moveLimit"
        }
        canvas.drawText(limitString,(Constant.screenW / 2 - 50).toFloat(),Constant.headerH *2 / 3 + 15,paint)
    }

    private fun checkRateScore(){
        val rate:Float = 100f / (scoreTarget[scoreTarget.lastIndex]).toFloat()
        scoreRate = rate * score
        if (scoreRate >= 100){
            scoreRate = 100f
        }
    }

}
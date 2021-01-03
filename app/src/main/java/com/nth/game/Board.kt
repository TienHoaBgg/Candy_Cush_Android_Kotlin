package com.nth.game

import android.app.Activity
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.nth.game.model.Candy
import com.nth.game.model.FileMap

/**
 * Created by NguyenTienHoa on 12/28/2020
 */

class Board {

    var x = 0
    var y = 0
    var row = 0
    var col = 0
    var width : Int
    var boardW : Int
    var boardH : Int
    var candy:CandyBits
    private var activity: Activity
    lateinit var fileMap:FileMap
    var listCandy:MutableList<Candy>
    var candys:Array<Array<Candy?>>


    constructor(activity: Activity, res: Resources){
        this.activity = activity

        val temp = FileManager.readMap(this.activity, "level1")
        if (temp != null){
            fileMap = temp
        }else{
            this.activity.finish()
        }
        listCandy = mutableListOf()
        this.row = fileMap.tileMap.size
        this.col = fileMap.tileMap[0].size

        boardW = Constant.screenW - Constant.spaceBoardWidth + Constant.spaceBoard*col
        width = boardW / col
        boardH = width * row + Constant.spaceBoard*row
        candy = CandyBits(width, res)

         x = Constant.screenW / 2 - boardW/2
         y = Constant.screenH / 2 - boardH/2


        candys = Array(col) { arrayOfNulls(row) }

        for (i in 0 until col) {
            for (j in 0 until row) {
                val tempX = x + i * width + Constant.spaceBoard
                val tempY = y + j * width + Constant.spaceBoard
                val typeCandy = fileMap.tileMap[j][i]
                if (typeCandy != 0){
                    candys[i][j] = Candy(
                        (tempX + Constant.spaceCandy / 2).toFloat(),
                        (tempY + Constant.spaceCandy / 2).toFloat(),
                        width - Constant.spaceCandy,
                        candy.getCandy(),
                        false,
                        false
                    )
                    listCandy.add(
                        Candy(
                            (tempX + Constant.spaceCandy / 2).toFloat(),
                            (tempY + Constant.spaceCandy / 2).toFloat(),
                            width - Constant.spaceCandy,
                            candy.getCandy(),
                            false,
                            false
                        )
                    )
                }
            }
        }
    }

    fun draw(canvas: Canvas, paint: Paint){
        val tempColor = paint.color
        paint.color = Constant.colorBgBoard
        canvas.drawRect(0f, 0f, Constant.screenW.toFloat(), 150f, paint)
        canvas.drawRect(
            (Constant.screenW / 4).toFloat(),
            (Constant.screenH - 200).toFloat(),
            Constant.screenW.toFloat(),
            Constant.screenH.toFloat(),
            paint
        )

        for (i in 0 until col) {
            for (j in 0 until row) {
                val tempX = x + i * width + Constant.spaceBoard
                val tempY = y + j * width + Constant.spaceBoard
                canvas.drawRect(
                    tempX.toFloat(),
                    tempY.toFloat(),
                    (x + width + i * width).toFloat(),
                    (y + width + j * width).toFloat(),
                    paint
                )
                val typeCandy = fileMap.tileMap[j][i]
                if (typeCandy == 0){
                    paint.color = Color.BLUE
                    canvas.drawBitmap(
                        candy.getRocks(),
                        (tempX + Constant.spaceCandy / 2).toFloat(),
                        (tempY + Constant.spaceCandy / 2).toFloat(),
                        paint
                    )
                    paint.color = Constant.colorBgBoard
                }
            }
        }
        paint.color = tempColor
    }
}
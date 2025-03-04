package com.nth.game.model

import android.app.Activity
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.nth.game.Constant
import com.nth.game.Utils
import com.nth.game.bitmap.CandyBitmap
import com.nth.game.bitmap.Footer
import com.nth.game.bitmap.Header

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
    var candy: CandyBitmap
    private var activity: Activity
    lateinit var fileMap:FileMap
    var listCandy:MutableList<Candy>
    var header:Header
    var footer:Footer
    constructor(activity: Activity,levelName : String, res: Resources){
        this.activity = activity
        val temp = Utils.readMap(this.activity, levelName)
        if (temp != null){
            fileMap = temp
        }else{
            this.activity.finish()
        }
        listCandy = mutableListOf()
        this.row = fileMap.tileMap.size
        this.col = fileMap.tileMap[0].size

        boardW = Constant.screenW - Constant.spaceBoardWidth + Constant.spaceBoard *col
        width = boardW / col
        boardH = width * row + Constant.spaceBoard *row
        candy = CandyBitmap(width, res)

         x = Constant.screenW / 2 - boardW/2
         y = Constant.screenH / 2 - boardH/2

        this.header = Header(fileMap.scoreTargets,res)
        this.footer = Footer(res)

        for (i in 0 until col) {
            for (j in 0 until row) {
                val tempX = x + i * width + Constant.spaceBoard
                val tempY = y + j * width + Constant.spaceBoard
                val typeCandy = fileMap.tileMap[j][i]
                if (typeCandy != 0 && typeCandy != -1){
                    listCandy.add(
                        Candy(
                            (tempX + Constant.spaceCandy / 2).toFloat(),
                            (tempY + Constant.spaceCandy / 2).toFloat(),
                            width - Constant.spaceCandy,
                            candy.getCandy(),
                                isSelect = false,
                                isMatch = true
                        )
                    )
                }
            }
        }
    }

    fun draw(canvas: Canvas, paint: Paint){
        val tempColor = paint.color
        header.draw(canvas,paint)
        footer.draw(canvas,paint)
        paint.color = Constant.colorBgBoard
        for (i in 0 until col) {
            for (j in 0 until row) {
                val tempX = x + i * width + Constant.spaceBoard
                val tempY = y + j * width + Constant.spaceBoard
                val typeCandy = fileMap.tileMap[j][i]
                if (typeCandy != -1) {
                    canvas.drawRect(
                            tempX.toFloat(),
                            tempY.toFloat(),
                            (x + width + i * width).toFloat(),
                            (y + width + j * width).toFloat(),
                            paint
                    )
                    if (typeCandy == 0) {
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
        }
        paint.color = tempColor
    }
}
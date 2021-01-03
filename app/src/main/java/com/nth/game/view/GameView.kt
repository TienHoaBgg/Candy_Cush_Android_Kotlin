package com.nth.game.view

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import com.nth.game.Background
import com.nth.game.Board
import com.nth.game.CandyBits
import com.nth.game.model.Candy


/**
 * Created by NguyenTienHoa on 12/23/2020
 */

class GameView : SurfaceView, Runnable {

    private var activity: PlayActivity

    private lateinit var thread: Thread
    private var isPlaying = false
    private var isGameOver = false
    private var background: Background
    private var paint: Paint
    private var board: Board
    private var candyBits: CandyBits
    private var listCandy: MutableList<Candy>
    private var candyArr: Array<Array<Candy?>>
    var index = 0

    constructor(activity: PlayActivity, screenW: Float, screenH: Float) : super(activity) {
        this.activity = activity
        background = Background(screenW, screenH, resources)
        paint = Paint()

        board = Board(activity, resources)
        listCandy = board.listCandy
        candyArr = board.candys
        candyBits = CandyBits(board.width, resources)
        index = (listCandy.size - 1)
    }

    override fun run() {
        while (isPlaying) {
            draw()
            update()
            sleep()
        }
    }


    private fun update() {

        checkMatchCol()
        checkMatchRow()
        getScore()

    }

    private fun getScore(){
        var countScore = 0
        for (candy in listCandy){
            if (candy.isMatch){
                countScore++
                candy.candy = candyBits.getCandy()
            }
            candy.isMatch = false
        }
       Log.i("Test","Score: ${60*countScore}")
    }

    private fun swapCandy(){
        var rowArr: Array<Array<Candy?>> = Array(board.row) { arrayOfNulls(listCandy.size) }
        var tempRow = 0
        var tempIdCandy = 0


    }



    private fun checkMatchRow() {
        var listRow: ArrayList<Int> = arrayListOf()
        for (i in listCandy.indices) {
            listRow.add(i)
            for (j in i + 1 until listCandy.size -1) {
                if (listCandy[i].y == listCandy[j].y) {
                    listRow.add(j)
                }
            }

            for (index in 0 until listRow.size - 2) {
                val typeCandy = listCandy[listRow[index]].candy.type
                if (typeCandy == listCandy[listRow[index + 1]].candy.type &&
                        typeCandy == listCandy[listRow[index + 2]].candy.type) {
                    for (k in 0..2) {
                        listCandy[listRow[index + k]].isMatch = true
                    }
                    var k = 3
                    while (index + k < listRow.size) {
                        if (typeCandy == listCandy[listRow[index + k]].candy.type) {
                            listCandy[listRow[index + k]].isMatch = true
                        } else {
                            break
                        }
                        k++
                    }
                }
            }
            listRow.clear()
        }
    }

    private fun checkMatchCol() {
        var listCol: ArrayList<Int> = arrayListOf()
        for (i in listCandy.indices) {
            listCol.add(i)
            for (j in i + 1 until listCandy.size -1) {
                if (listCandy[i].x == listCandy[j].x) {
                    listCol.add(j)
                }
            }

            for (index in 0 until listCol.size - 2) {
                val typeCandy = listCandy[listCol[index]].candy.type
                if (typeCandy == listCandy[listCol[index + 1]].candy.type &&
                        typeCandy == listCandy[listCol[index + 2]].candy.type) {
                    for (k in 0..2) {
                        listCandy[listCol[index + k]].isMatch = true
                    }
                    var k = 3
                    while (index + k < listCol.size) {
                        if (typeCandy == listCandy[listCol[index + k]].candy.type) {
                            listCandy[listCol[index + k]].isMatch = true
                        } else {
                            break
                        }
                        k++
                    }
                }
            }
            listCol.clear()
        }
    }



    private var rowSelect = 0
    private var colSelect = 0
    private var candySelectId = 0
    private fun touchCandy(point: Point) {
//        for (i in 0..board.col) {
//            for (j in 0..board.row) {
//                val candy = candyArr[i][j]!!
//                if (point.x >= candy.x && point.x <= candy.x + candy.width && point.y >= candy.y && point.y <= candy.y + candy.width) {
//                    candy.isSelect = !candy.isSelect
//                    rowSelect = j
//                    colSelect = i
//                } else {
//                    candy.isSelect = false
//                }
//            }
//        }

        for (i in listCandy.indices) {
            val candy = listCandy[i]
            if (point.x >= candy.x && point.x <= candy.x + candy.width && point.y >= candy.y && point.y <= candy.y + candy.width) {
                candy.isSelect = !candy.isSelect
                candySelectId = i
            } else {
                candy.isSelect = false
            }
        }

    }

    private fun moveCandy(point: Point) {
//        for (i in 0..board.col) {
//            for (j in 0..board.row) {
//                if (point.x >= candyArr[i][j]!!.x && point.x <= candyArr[i][j]!!.x + candyArr[i][j]!!.width && point.y >= candyArr[i][j]!!.y && point.y <= candyArr[i][j]!!.y + candyArr[i][j]!!.width) {
//                    val temp = candyArr[i][j]!!.candy
//                    candyArr[i][j]!!.candy = candyArr[colSelect][rowSelect]!!.candy
//                    candyArr[colSelect][rowSelect]!!.candy = temp
//                    candyArr[colSelect][rowSelect]!!.isSelect = false
//                }
//            }
//        }

        for (i in listCandy.indices) {
            if (point.x >= listCandy[i].x && point.x <= listCandy[i].x + listCandy[i].width && point.y >= listCandy[i].y && point.y <= listCandy[i].y + listCandy[i].width) {
                val temp = listCandy[i].candy
                listCandy[i].candy = listCandy[candySelectId].candy
                listCandy[candySelectId].candy = temp
                listCandy[candySelectId].isSelect = false
            }
        }
    }

    private var candyS = false
    private fun draw() {
        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawBitmap(background.background, background.x, background.y, paint)
            board.draw(canvas, paint)
            paint.color = Color.BLUE

            for (candy in listCandy) {
                if (!candy.isMatch) {
                    if (candy.isSelect) {
                        if (candyS) {
                            canvas.drawBitmap(
                                    Bitmap.createScaledBitmap(
                                            candy.candy.bitmap,
                                            candyBits.width - 10,
                                            candyBits.width - 10,
                                            false
                                    ), candy.x, candy.y, paint
                            )
                        } else {
                            canvas.drawBitmap(candy.candy.bitmap, candy.x, candy.y, paint)
                        }
                        candyS = !candyS
                    } else {
                        canvas.drawBitmap(candy.candy.bitmap, candy.x, candy.y, paint)
                    }
                }
            }
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private var touchTempX = 0f
    private var touchTempY = 0f
    private var isTouch = false
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.x > board.x && event.x < (board.x + board.boardW) && event.y > board.y && event.y < (board.y + board.boardH)) {

            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    if (isTouch) {
                        if (event.x > touchTempX + listCandy[0].width && event.y > touchTempY - listCandy[0].width / 2 && event.y < touchTempY + listCandy[0].width / 2) {
                            isTouch = false
                            moveCandy(Point(event.x.toInt(), event.y.toInt()))
                        } else if (event.x < touchTempX - listCandy[0].width && event.y > touchTempY - listCandy[0].width / 2 && event.y < touchTempY + listCandy[0].width / 2) {
                            isTouch = false
                            moveCandy(Point(event.x.toInt(), event.y.toInt()))
                        } else if (event.y > touchTempY + listCandy[0].width && event.x > touchTempX - listCandy[0].width / 2 && event.x < touchTempX + listCandy[0].width / 2) {
                            isTouch = false
                            moveCandy(Point(event.x.toInt(), event.y.toInt()))
                        } else if (event.y < touchTempY - listCandy[0].width && event.x > touchTempX - listCandy[0].width / 2 && event.x < touchTempX + listCandy[0].width / 2) {
                            isTouch = false
                            moveCandy(Point(event.x.toInt(), event.y.toInt()))
                        }
                    }
                }
                MotionEvent.ACTION_DOWN -> {
                    isTouch = true
                    touchTempX = event.x
                    touchTempY = event.y
                    touchCandy(Point(event.x.toInt(), event.y.toInt()))
                }
            }
        }
        return true
    }

    private fun sleep() {
        try {
            Thread.sleep(111)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun resume() {
        isPlaying = true
        thread = Thread(this)
        thread.start()
    }

    fun pause() {
        try {
            isPlaying = false
            thread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

}
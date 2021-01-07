package com.nth.game.view

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.view.MotionEvent
import android.view.SurfaceView
import com.nth.game.model.Background
import com.nth.game.model.Board
import com.nth.game.model.CandyBitmap
import com.nth.game.Constant
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
    private var candyBitmap: CandyBitmap
    private var listCandy: MutableList<Candy>
    private var listCol :MutableList<Int>
    private var score = 0
    private var moveCount = 0
    var index = 0

    constructor(activity: PlayActivity, screenW: Float, screenH: Float) : super(activity) {
        this.activity = activity
        background = Background(screenW, screenH, resources)
        paint = Paint()
        listCol = arrayListOf()
        board = Board(activity,"level1", resources)
        moveCount = board.fileMap.moveLimit
        listCandy = board.listCandy

        candyBitmap = CandyBitmap(board.width, resources)
        index = (listCandy.size - 1)
        listCol.add(0)
        for (i in 1..listCandy.size - 2){
            if (listCandy[0].y == listCandy[i].y){
                listCol.add(i)
            }
            if (listCol.size == board.col){
                break
            }
        }
    }

    override fun run() {
        while (isPlaying) {
            draw()
            checkMatch()
            dropDownCandy()
            checkEndGame()
            sleep()
        }
    }

    private fun checkEndGame(){
        if (moveCount <= 0){
            var isRun = false
            for (candy in listCandy){
                if (candy.isMatch){
                    isRun = true
                    break
                }
            }
            if (!isRun && checkMatch() == 0){
                isGameOver = true
            }
        }
    }

    private fun dropDownCandy(){
        for (j in 0 until listCol.size - 1){
            for (k in listCol[j] until listCol[j+1] - 1){
                if (listCandy[listCol[j]].isMatch){
                    listCandy[listCol[j]].candy = candyBitmap.getCandy()
                    listCandy[listCol[j]].isMatch = false
                }
                if (listCandy[k+1].isMatch){
                       var temp = listCandy[k].candy
                       listCandy[k].candy = listCandy[k + 1].candy
                        listCandy[k].isMatch = true
                       listCandy[k+1].candy = temp
                        listCandy[k+1].isMatch = false
                }
            }
        }

        // hieu ung cho cot cuoi cung
        for (k in listCol[listCol.size -1] until listCandy.size - 1){
            if (listCandy[listCol[listCol.size -1]].isMatch){
                listCandy[listCol[listCol.size -1]].candy = candyBitmap.getCandy()
                listCandy[listCol[listCol.size -1]].isMatch = false
            }
            if (listCandy[k+1].isMatch){
                var temp = listCandy[k].candy
                listCandy[k].candy = listCandy[k + 1].candy
                listCandy[k].isMatch = true
                listCandy[k+1].candy = temp
                listCandy[k+1].isMatch = false
            }
        }
    }

    private fun checkMatch() : Int {
        // check an diem theo hang
        var listRow: ArrayList<Int> = arrayListOf()
        var scoreCount = 0
        for (i in listCandy.indices) {
            listRow.add(i)
            // Luu cac phan tu theo hang thanh 1 mang rieng de xac dinh cac phan tu dung canh nhau
            for (j in i + 1 until listCandy.size) {
                if (listCandy[i].y == listCandy[j].y) {
                    listRow.add(j)
                }
            }
            // Dung vong lap de xet cac phan tu dung canh nhau co tao thanh > 3 cac phan tu giong nhau khong de tinh diem
            for (index in 0 until listRow.size - 2) {
                val typeCandy = listCandy[listRow[index]].candy.type
                if (typeCandy == listCandy[listRow[index + 1]].candy.type &&
                        typeCandy == listCandy[listRow[index + 2]].candy.type) {
                            // Danh dau cac candy cung kieu dung canh nhau
                    for (k in 0..2) {
                            // Do la dung mang 1 chieu nen can lap lai nhieu lan de xac dinh
                                // Nen dung if o day de bo qua cac candy da duoc xac dinh o vong lap truoc
                        if (!listCandy[listRow[index + k]].isMatch){
                            scoreCount++
                            listCandy[listRow[index + k]].isMatch = true
                        }
                    }

                    // kiem tra them cac candy dung canh 3 phan tu da kiem tra o tren
                    // de tinh cac truong hop co 4-5.. candy giong nhau dung canh nhau
                    var k = 3
                    while (index + k < listRow.size) {
                        if (typeCandy == listCandy[listRow[index + k]].candy.type) {
                            if (!listCandy[listRow[index + k]].isMatch){
                                scoreCount++
                                listCandy[listRow[index + k]].isMatch = true
                            }
                        } else {
                            break
                        }
                        k++
                    }
                }
            }
            listRow.clear()
        }

        // check an diem theo cot
        // Kiem tra tuong tu nhu theo hang o tren
        var listCol: ArrayList<Int> = arrayListOf()
        for (i in listCandy.indices) {
            listCol.add(i)
            // Luu cac phan tu theo cot thanh 1 mang rieng de xac dinh cac phan tu dung canh nhau
            for (j in i + 1 until listCandy.size) {
                if (listCandy[i].x == listCandy[j].x) {
                    listCol.add(j)
                }
            }

            for (index in 0 until listCol.size - 2) {
                val typeCandy = listCandy[listCol[index]].candy.type
                if (typeCandy == listCandy[listCol[index + 1]].candy.type &&
                        typeCandy == listCandy[listCol[index + 2]].candy.type) {
                    for (k in 0..2) {
                        if (!listCandy[listCol[index + k]].isMatch){
                            scoreCount++
                            listCandy[listCol[index + k]].isMatch = true
                        }
                    }
                    var k = 3
                    while (index + k < listCol.size) {
                        if (typeCandy == listCandy[listCol[index + k]].candy.type) {
                            if (!listCandy[listCol[index + k]].isMatch){
                                scoreCount++
                                listCandy[listCol[index + k]].isMatch = true
                            }
                        } else {
                            break
                        }
                        k++
                    }
                }
            }
            listCol.clear()
        }
        score += Constant.score*scoreCount
        return Constant.score*scoreCount
    }

    private var candySelectId = 0
    private fun touchCandy(point: Point) {
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
        for (i in listCandy.indices) {
            if (point.x >= listCandy[i].x && point.x <= listCandy[i].x + listCandy[i].width && point.y >= listCandy[i].y && point.y <= listCandy[i].y + listCandy[i].width) {
                Thread {
                    var temp = listCandy[i].candy
                    listCandy[i].candy = listCandy[candySelectId].candy
                    listCandy[candySelectId].candy = temp
                    // Kiem tra sau khi di chuyen candy co match khong
                    // Neu khong thi di chuyen lai vi tri cu
                    if (checkMatch() <= 0){
                        Thread.sleep(220)
                        temp = listCandy[i].candy
                        listCandy[i].candy = listCandy[candySelectId].candy
                        listCandy[candySelectId].candy = temp
                    }else{
                        moveCount--
                    }
                    listCandy[candySelectId].isSelect = false
                }.start()
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
                // Ve tat ca cac candy ma chua match
                if (!candy.isMatch) {
                    if (candy.isSelect) {
                        // Tao hieu ung nhap nhay candy khi duoc chon
                        if (candyS) {
                            canvas.drawBitmap(
                                    Bitmap.createScaledBitmap(
                                            candy.candy.bitmap,
                                            candyBitmap.width - 10,
                                            candyBitmap.width - 10,
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

            paint.textSize = 100f
            canvas.drawText("$score",((Constant.screenW * 3 / 5) ).toFloat(),(Constant.screenH - 69).toFloat(),paint )
            paint.color = Color.RED
            canvas.drawText("$moveCount",((Constant.screenW  / 2) ).toFloat(),(Constant.screenH - 69).toFloat(),paint )

            if (isGameOver){
                isPlaying = false
                paint.textSize = 160f
                paint.color = Color.WHITE
                canvas.drawText("Game Over",((Constant.screenW  / 4)).toFloat(),(Constant.screenH/2).toFloat(),paint )

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
                        // Candy sang phai
                        if (event.x > touchTempX + listCandy[0].width && event.y > touchTempY - listCandy[0].width / 2 && event.y < touchTempY + listCandy[0].width / 2) {
                            isTouch = false
                            moveCandy(Point(event.x.toInt(), event.y.toInt()))

                        }// Candy sang trai
                        else if (event.x < touchTempX - listCandy[0].width && event.y > touchTempY - listCandy[0].width / 2 && event.y < touchTempY + listCandy[0].width / 2) {
                            isTouch = false
                            moveCandy(Point(event.x.toInt(), event.y.toInt()))
                        }
                        // Candy di xuong
                        else if (event.y > touchTempY + listCandy[0].width && event.x > touchTempX - listCandy[0].width / 2 && event.x < touchTempX + listCandy[0].width / 2) {
                            isTouch = false
                            moveCandy(Point(event.x.toInt(), event.y.toInt()))
                        } // Candy di len
                        else if (event.y < touchTempY - listCandy[0].width && event.x > touchTempX - listCandy[0].width / 2 && event.x < touchTempX + listCandy[0].width / 2) {
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
            Thread.sleep(122)
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
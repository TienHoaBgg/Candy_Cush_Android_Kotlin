package com.nth.game.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.*
import android.os.Build
import android.view.MotionEvent
import android.view.SurfaceView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.nth.game.Constant
import com.nth.game.R
import com.nth.game.bitmap.Background
import com.nth.game.bitmap.CandyBitmap
import com.nth.game.bitmap.EndGame
import com.nth.game.manager.DataManager
import com.nth.game.manager.MusicManager
import com.nth.game.manager.SoundManager
import com.nth.game.model.Board
import com.nth.game.model.Candy
import com.nth.game.model.Level


/**
 * Created by NguyenTienHoa on 12/23/2020
 */

class GameView : SurfaceView, Runnable {

    private var activity: PlayActivity

    private lateinit var thread: Thread
    private var sleep: Long = 100
    private var isPlaying = false
    private var isGameOver = false
    private var background: Background
    private var paint: Paint
    private lateinit var board: Board
    private lateinit var candyBitmap: CandyBitmap
    private lateinit var listCandy: MutableList<Candy>
    private lateinit var listCol: MutableList<Int>
    private var score: Long = 0
    private var moveCount = 0
    var index = 0
    private var musicManager: MusicManager
    private var prefs: SharedPreferences
    private var soundManager: SoundManager
    private var endGame: EndGame
    private var currentLevel: Level
    private var bgBoard: Bitmap? = null

    constructor(activity: PlayActivity, level: Level, screenW: Float, screenH: Float) : super(activity) {
        this.activity = activity
        prefs = activity.getSharedPreferences("GameCandy", Context.MODE_PRIVATE)
        currentLevel = level

        musicManager = MusicManager()
        soundManager = SoundManager()

        musicManager.setData(activity, R.raw.music_bg_monkey)

        background = Background(screenW, screenH, resources)
        init()

        paint = Paint()
        endGame = EndGame(resources)

        val customTypeface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            resources.getFont(R.font.mergepro)
        } else {
            ResourcesCompat.getFont(context, R.font.mergepro)
        }
        paint.typeface = customTypeface
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 5F
        if (!prefs.getBoolean("isMusicOff", false)) musicManager.play()
    }

    private fun reloadGame() {
        soundManager.buttonPress()
        isGameOver = false
        init()
    }

    private fun backToHome() {
        soundManager.buttonPress()
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    private fun nextLevel(){
        val newLevel = DataManager.getInstances().getCurrentLevelId() + 1
        if (newLevel >= Constant.listLevel.size){
            Toast.makeText(activity, "Chúc mừng bạn đã chơi đến level cuối cùng.", Toast.LENGTH_SHORT).show()
        }else{
            currentLevel = Constant.listLevel[newLevel]
            endGame.tempY = 0f
            endGame.isDone = false
            DataManager.getInstances().saveCurrentLevel(newLevel)
            reloadGame()
            resume()
        }
    }

    private fun init() {
        listCol = arrayListOf()
        score = 0
        board = Board(activity, currentLevel.fileName, resources)
        board.header.isMusicOff = prefs.getBoolean("isMusicOff", false)
        board.header.isSoundOff = prefs.getBoolean("isSoundOff", false)
        bgBoard = background.getBackgroundRd()
        moveCount = board.fileMap.moveLimit
        listCandy = board.listCandy

        candyBitmap = CandyBitmap(board.width, resources)

        index = (listCandy.size - 1)
        listCol.add(0)
        for (i in 1..listCandy.size - 2) {
            if (listCandy[0].y == listCandy[i].y) {
                listCol.add(i)
            }
            if (listCol.size == board.col) {
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
            update()
            sleep()
        }
    }

    private fun update() {
        board.header.score = score
        board.header.moveLimit = moveCount
        board.header.levelName = currentLevel.name
        currentLevel.score = score
        currentLevel.typeView = board.header.typeView
    }

    private fun checkEndGame() {
        if (moveCount <= 0) {
            var isRun = false
            for (candy in listCandy) {
                if (candy.isMatch) {
                    isRun = true
                    break
                }
            }
            if (!isRun && checkMatch() == 0) {
                Thread {
                    DataManager.getInstances().saveHighScore(currentLevel)
                    Thread.sleep(1000)
                    isGameOver = true
                }.start()
            }
        }
    }

    private fun dropDownCandy() {
        if (listCol.size != 0) {
            for (j in 0 until listCol.size - 1) {
                for (k in listCol[j] until listCol[j + 1] - 1) {
                    if (listCandy[listCol[j]].isMatch) {
                        listCandy[listCol[j]].candy = candyBitmap.getCandy()
                        listCandy[listCol[j]].isMatch = false
                    }
                    if (listCandy[k + 1].isMatch) {
                        var temp = listCandy[k].candy
                        listCandy[k].candy = listCandy[k + 1].candy
                        listCandy[k].isMatch = true
                        listCandy[k + 1].candy = temp
                        listCandy[k + 1].isMatch = false
                    }
                }
            }

            // hieu ung cho cot cuoi cung
            for (k in listCol[listCol.size - 1] until listCandy.size - 1) {
                if (listCandy[listCol[listCol.size - 1]].isMatch) {
                    listCandy[listCol[listCol.size - 1]].candy = candyBitmap.getCandy()
                    listCandy[listCol[listCol.size - 1]].isMatch = false
                }
                if (listCandy[k + 1].isMatch) {
                    var temp = listCandy[k].candy
                    listCandy[k].candy = listCandy[k + 1].candy
                    listCandy[k].isMatch = true
                    listCandy[k + 1].candy = temp
                    listCandy[k + 1].isMatch = false
                }
            }
        }
    }

    private fun checkMatch(): Int {
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
                        typeCandy == listCandy[listRow[index + 2]].candy.type && !listCandy[listRow[index + 1]].isMatch && !listCandy[listRow[index + 2]].isMatch) {
                    // Danh dau cac candy cung kieu dung canh nhau
                    soundManager.match1()
                    for (k in 0..2) {
                        // Do la dung mang 1 chieu nen can lap lai nhieu lan de xac dinh
                        // Nen dung if o day de bo qua cac candy da duoc xac dinh o vong lap truoc
                        if (!listCandy[listRow[index + k]].isMatch) {
                            scoreCount++
                            listCandy[listRow[index + k]].isMatch = true
                        }
                    }

                    // kiem tra them cac candy dung canh 3 phan tu da kiem tra o tren
                    // de tinh cac truong hop co 4-5.. candy giong nhau dung canh nhau
                    var k = 3
                    while (index + k < listRow.size) {
                        if (typeCandy == listCandy[listRow[index + k]].candy.type) {
                            scoreCount++
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
                        typeCandy == listCandy[listCol[index + 2]].candy.type &&
                        !listCandy[listCol[index + 1]].isMatch && !listCandy[listCol[index + 2]].isMatch) {
                    soundManager.match1()
                    for (k in 0..2) {
                        listCandy[listCol[index + k]].isMatch = true
                        scoreCount++
                    }
                    var k = 3
                    while (index + k < listCol.size) {
                        if (typeCandy == listCandy[listCol[index + k]].candy.type) {
                            if (!listCandy[listCol[index + k]].isMatch) {
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
        score += board.fileMap.score * scoreCount
        return board.fileMap.score * scoreCount
    }

    private var candySelectId = 0
    private fun touchCandy(point: Point): Point? {
        var pointCandy: Point? = null
        for (i in listCandy.indices) {
            val candy = listCandy[i]
            if (point.x >= candy.x && point.x <= candy.x + candy.width && point.y >= candy.y && point.y <= candy.y + candy.width) {
                candy.isSelect = !candy.isSelect
                candySelectId = i
                pointCandy = Point((candy.x + candy.width / 2).toInt(), (candy.y + candy.width / 2).toInt())
                if (!prefs.getBoolean("isSoundOff", false)) soundManager.buttonPress()
            } else {
                candy.isSelect = false
            }
        }
        return pointCandy
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
                    if (checkMatch() <= 0) {
                        if (!prefs.getBoolean("isSoundOff", false)) soundManager.playMoveCancel()
                        Thread.sleep(220)
                        temp = listCandy[i].candy
                        listCandy[i].candy = listCandy[candySelectId].candy
                        listCandy[candySelectId].candy = temp
                    } else {
                        if (!prefs.getBoolean("isSoundOff", false)) soundManager.playMoveAccept()
                        moveCount--
                    }
                    listCandy[candySelectId].isSelect = false
                }.start()
            }
        }
    }

    private fun onClickSound() {
        val editor = prefs.edit()
        editor.putBoolean("isSoundOff", !prefs.getBoolean("isSoundOff", false))
        editor.apply()
        if (!prefs.getBoolean("isSoundOff", false)) soundManager.buttonPress()
    }

    private fun onClickMusic() {
        if (!prefs.getBoolean("isSoundOff", false)) soundManager.buttonPress()
        val editor = prefs.edit()
        editor.putBoolean("isMusicOff", !prefs.getBoolean("isMusicOff", false))
        editor.apply()
        if (prefs.getBoolean("isMusicOff", false)) {
            musicManager.pause()
        } else {
            musicManager.play()
        }
    }

    private var candyS = false
    private fun draw() {
        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawBitmap(bgBoard!!, background.x, background.y, paint)
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

            if (isGameOver) {
                sleep = 5
                paint.textSize = 160f
                paint.color = Color.argb(69, 255, 255, 255)
                canvas.drawRect(0f, 0f, Constant.screenW.toFloat(), Constant.screenH.toFloat() + 120f, paint)
                endGame.draw(currentLevel, canvas, paint)
                if (endGame.isDone) {
                    sleep = 100
                    isPlaying = false
                }
            }

            holder.unlockCanvasAndPost(canvas)
        }
    }

    private var touchTempX = 0f
    private var touchTempY = 0f
    private var isTouch = false
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isGameOver && moveCount > 0) {
            touchIsPlay(event)
        } else {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (event.x > endGame.btnHome.x && event.x < endGame.btnHome.x + endGame.btnHome.width &&
                            event.y > endGame.btnHome.y && event.y < endGame.btnHome.y + endGame.btnHome.width) {
                        backToHome()
                    }
                    if (event.x > endGame.btnReload.x && event.x < endGame.btnReload.x + endGame.btnReload.width &&
                            event.y > endGame.btnReload.y && event.y < endGame.btnReload.y + endGame.btnReload.width) {
                        endGame.tempY = 0f
                        endGame.isDone = false
                        reloadGame()
                        resume()
                    }

                    if (event.x > endGame.btnNext.x && event.x < endGame.btnNext.x + endGame.btnNext.width &&
                            event.y > endGame.btnNext.y && event.y < endGame.btnNext.y + endGame.btnNext.width) {
                        nextLevel()
                    }

                    if (event.x > endGame.btnClose.x && event.x < endGame.btnClose.x + endGame.btnClose.width &&
                            event.y > endGame.btnClose.y && event.y < endGame.btnClose.y + endGame.btnClose.width) {
                        activity.finish()
                    }
                }
            }
        }
        return true
    }

    private fun touchIsPlay(event: MotionEvent) {
        if (event.x > board.x && event.x < (board.x + board.boardW) && event.y > board.y && event.y < (board.y + board.boardH)) {
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    if (isTouch) {
                        // Candy sang phai
                        if (event.x > touchTempX + listCandy[0].width && event.y > touchTempY - listCandy[0].width / 2
                                && event.y < touchTempY + listCandy[0].width / 2) {
                            isTouch = false
                            moveCandy(Point(event.x.toInt(), event.y.toInt()))

                        }// Candy sang trai
                        else if (event.x < touchTempX - listCandy[0].width && event.y > touchTempY - listCandy[0].width / 2
                                && event.y < touchTempY + listCandy[0].width / 2) {
                            isTouch = false
                            moveCandy(Point(event.x.toInt(), event.y.toInt()))
                        }
                        // Candy di xuong
                        else if (event.y > touchTempY + listCandy[0].width && event.x > touchTempX - listCandy[0].width / 2
                                && event.x < touchTempX + listCandy[0].width / 2) {
                            isTouch = false
                            moveCandy(Point(event.x.toInt(), event.y.toInt()))
                        } // Candy di len
                        else if (event.y < touchTempY - listCandy[0].width && event.x > touchTempX - listCandy[0].width / 2
                                && event.x < touchTempX + listCandy[0].width / 2) {
                            isTouch = false
                            moveCandy(Point(event.x.toInt(), event.y.toInt()))
                        }
                    }
                }
                MotionEvent.ACTION_DOWN -> {
                    isTouch = true
                    val pointCandySelect = touchCandy(Point(event.x.toInt(), event.y.toInt()))
                    if (pointCandySelect != null) {
                        touchTempX = pointCandySelect.x.toFloat()
                        touchTempY = pointCandySelect.y.toFloat()
                    }
                }
            }
        }
        // Cham phia ngoai man choi
        else {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (event.x >= board.header.soundOn.x && event.x < board.header.soundOn.x + board.header.soundOn.width
                            && event.y > 6 && event.y < board.header.soundOn.y + board.header.soundOn.width) {
                        board.header.isSoundOff = !board.header.isSoundOff
                        onClickSound()
                    }
                    if (event.x >= board.header.musicOn.x && event.x < board.header.musicOn.x + board.header.musicOn.width
                            && event.y > 6 && event.y < board.header.musicOn.y + board.header.musicOn.width) {
                        board.header.isMusicOff = !board.header.isMusicOff
                        onClickMusic()
                    }

                    if (event.x >= board.footer.btnHome.x && event.x < board.footer.btnHome.x + board.footer.btnHome.width
                            && event.y > board.footer.btnHome.y && event.y < board.footer.btnHome.y + board.footer.btnHome.width) {
                        backToHome()
                    }
                    if (event.x >= board.footer.btnReload.x && event.x < board.footer.btnReload.x + board.footer.btnReload.width
                            && event.y > board.footer.btnReload.y && event.y < board.footer.btnReload.y + board.footer.btnReload.width) {
                        reloadGame()
                    }

                }
            }
        }
    }

    private fun sleep() {
        try {
            Thread.sleep(sleep)
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
            if (musicManager.isPlay()) {
                musicManager.stop()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun destroy() {
        musicManager.release()
        soundManager.release()
    }

}
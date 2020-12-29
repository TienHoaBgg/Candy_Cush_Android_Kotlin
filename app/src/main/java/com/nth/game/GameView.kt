package com.nth.game

import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceView

/**
 * Created by NguyenTienHoa on 12/23/2020
 */

class GameView : SurfaceView , Runnable {

    private var activity: PlayActivity
    private var screenW : Float
    private  var screenH : Float
    private lateinit var thread: Thread
    private var isPlaying = false
    private var isGameOver = false
    private var background : Background
    private var paint:Paint
    private var board : Board

    constructor(activity: PlayActivity, screenW: Float, screenH: Float) : super(activity) {
        this.activity = activity
        this.screenW = screenW
        this.screenH = screenH
        background = Background(screenW,screenH,resources)
        paint = Paint()
        board = Board(screenW,screenH,resources)
    }

    override fun run() {
        while (isPlaying){
            sleep()
            draw()
            update()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return true
    }

    private fun draw() {
        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawBitmap(background.background,background.x,background.y,paint)
            paint.color = Color.BLUE
            board.draw(canvas,paint)
            isPlaying = false
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun update(){


    }

    private fun sleep() {
        try {
            Thread.sleep(20)
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
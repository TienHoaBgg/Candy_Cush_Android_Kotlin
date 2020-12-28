package com.nth.game

import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceView

/**
 * Created by NguyenTienHoa on 12/23/2020
 */

class GameView : SurfaceView , Runnable {

    private var activity: PlayActivity
    private var screenX : Float
    private  var screenY : Float
    private lateinit var thread: Thread
    private var isPlaying = false
    private var isGameOver = false
    private var background : Background
    private var paint:Paint

    constructor(activity: PlayActivity, screenX: Float, screenY: Float) : super(activity) {
        this.activity = activity
        this.screenX = screenX
        this.screenY = screenY
        background = Background(screenX,screenY,resources)
        paint = Paint()
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
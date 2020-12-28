package com.nth.game

import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by NguyenTienHoa on 12/23/2020
 */

class PlayActivity : AppCompatActivity() {

    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        gameView = GameView(this, point.x.toFloat(), point.y.toFloat())
        setContentView(gameView)
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }
}
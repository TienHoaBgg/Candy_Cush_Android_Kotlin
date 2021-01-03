package com.nth.game.view

import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nth.game.Constant
import com.nth.game.FileManager

/**
 * Created by NguyenTienHoa on 12/23/2020
 */

class PlayActivity : AppCompatActivity() {

    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val point = Point()
        windowManager.defaultDisplay.getSize(point)

        Constant.screenW = point.x
        Constant.screenH = point.y - getHNavigationBar()

        gameView = GameView(this, point.x.toFloat(), point.y.toFloat())
        setContentView(gameView)
    }

    private fun getHNavigationBar(): Int{
        val resourceId: Int = this.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
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
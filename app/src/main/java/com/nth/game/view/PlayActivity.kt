package com.nth.game.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.nth.game.Constant
import com.nth.game.R
import com.nth.game.model.Level
import kotlinx.android.synthetic.main.activity_select_level.*

/**
 * Created by NguyenTienHoa on 12/23/2020
 */

class PlayActivity : AppCompatActivity() {
    private lateinit var gameView: GameView
    private lateinit var point:Point

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         point = Point()
        windowManager.defaultDisplay.getSize(point)

        Constant.screenW = point.x
        Constant.screenH = point.y - getHNavigationBar()
        val level = Gson().fromJson(intent.getStringExtra("LEVEL"),Level::class.java)

        gameView = GameView(this, level,point.x.toFloat(), point.y.toFloat())
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

    override fun onDestroy() {
        super.onDestroy()
        gameView.destroy()
    }
}
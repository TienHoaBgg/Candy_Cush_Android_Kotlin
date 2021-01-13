package com.nth.game.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nth.game.R
import com.nth.game.databinding.ActivityMainBinding
import com.nth.game.manager.SoundManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var playActivity: PlayActivity
    private lateinit var settingActivity: SettingActivity
    private lateinit var highScoreActivity: HighScoreActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        playActivity = PlayActivity()
        settingActivity = SettingActivity()
        highScoreActivity = HighScoreActivity()

        binding.btnPlay.setOnClickListener {
            SoundManager.getInstance().buttonPress()
            startActivity(Intent(this@MainActivity, playActivity::class.java))
        }

        binding.btnHighScore.setOnClickListener {
            SoundManager.getInstance().buttonPress()
            startActivity(Intent(this@MainActivity, highScoreActivity::class.java))
        }

        binding.btnSetting.setOnClickListener {
            SoundManager.getInstance().buttonPress()
            startActivity(Intent(this@MainActivity, settingActivity::class.java))
        }

        binding.btnExit.setOnClickListener {
            SoundManager.getInstance().buttonPress()
            this.finish()
        }

    }

}
package com.nth.game.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nth.game.R
import com.nth.game.databinding.ActivityMainBinding
import com.nth.game.manager.SoundManager
import com.nth.game.view.highscore.HighScoreActivity
import com.nth.game.view.selectlevel.SelectLevelActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var settingActivity: SettingActivity
    private lateinit var highScoreActivity: HighScoreActivity
    private lateinit var selectLevelActivity: SelectLevelActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        settingActivity = SettingActivity()
        highScoreActivity = HighScoreActivity()
        selectLevelActivity = SelectLevelActivity()

        binding.btnPlay.setOnClickListener {
            SoundManager.getInstance().buttonPress()
            startActivity(Intent(this@MainActivity, selectLevelActivity::class.java))
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
package com.nth.game.view.highscore

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nth.game.R
import com.nth.game.databinding.ActivityHighScoreBinding
import com.nth.game.manager.SoundManager

/**
 * Created by NguyenTienHoa on 1/11/2021
 */

class HighScoreActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHighScoreBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_high_score)

        binding.rycHighScore.apply {
            layoutManager = LinearLayoutManager(this@HighScoreActivity)
            adapter = HighScoreAdapter()
        }

        binding.btnBack.setOnClickListener {
            SoundManager.getInstance().buttonPress()
            this.finish()
        }
    }

}
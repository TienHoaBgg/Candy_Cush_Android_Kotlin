package com.nth.game.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nth.game.R
import com.nth.game.databinding.ActivitySettingBinding
import com.nth.game.manager.DataManager
import com.nth.game.manager.SoundManager

/**
 * Created by NguyenTienHoa on 1/10/2021
 */

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)

        binding.btnSound.isChecked = !DataManager.getInstances().getStateSound()
        binding.btnMusic.isChecked = !DataManager.getInstances().getStateMusic()

        binding.btnSound.setOnCheckedChangeListener { _, _ ->
            SoundManager.getInstance().buttonPress()
            DataManager.getInstances().changeStateSound()
        }
        binding.btnMusic.setOnCheckedChangeListener { _, _ ->
            SoundManager.getInstance().buttonPress()
            DataManager.getInstances().changeStateMusic()

        }

        binding.btnBack.setOnClickListener {
            SoundManager.getInstance().buttonPress()
            this.finish()
        }

    }

}
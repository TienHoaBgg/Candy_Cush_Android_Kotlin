package com.nth.game.view.selectlevel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.nth.game.Constant
import com.nth.game.R
import com.nth.game.Utils
import com.nth.game.databinding.ActivitySelectLevelBinding
import com.nth.game.manager.DataManager
import com.nth.game.model.Level
import com.nth.game.view.PlayActivity

/**
 * Created by NguyenTienHoa on 1/13/2021
 */

class SelectLevelActivity : AppCompatActivity() , Adapter.IAdapter{
    private lateinit var binding:ActivitySelectLevelBinding
    private lateinit var listLevel:MutableList<Level>
    private lateinit var playActivity: PlayActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_level)
        listLevel = Constant.listLevel as MutableList<Level>

        playActivity = PlayActivity()

        binding.rycLevel.apply {
            layoutManager = LinearLayoutManager(this@SelectLevelActivity)
            adapter = Adapter(this@SelectLevelActivity)
        }
    }

    override fun onClick(position: Int) {
        DataManager.getInstances().saveCurrentLevel(position)
        val intent = Intent(this,playActivity::class.java)
        intent.putExtra("LEVEL", Gson().toJson(listLevel[position]))
        startActivity(intent)
    }

    override fun getList() = listLevel

}
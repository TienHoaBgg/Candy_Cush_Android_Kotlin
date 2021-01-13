package com.nth.game.view.highscore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nth.game.databinding.ItemLevelBinding
import com.nth.game.manager.DataManager
import com.nth.game.model.Level
import com.nth.game.view.selectlevel.Adapter

/**
 * Created by NguyenTienHoa on 1/13/2021
 */

class HighScoreAdapter : RecyclerView.Adapter<HighScoreAdapter.ViewHolder>(){
    private lateinit var binding: ItemLevelBinding
    private var listScore = DataManager.getInstances().getHighScore()

    class ViewHolder : RecyclerView.ViewHolder {
        val binding : ItemLevelBinding
        constructor(binding: ItemLevelBinding) : super(binding.root){
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       binding = ItemLevelBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.item = listScore[position]
    }

    override fun getItemCount(): Int {
        return listScore.size
    }

}
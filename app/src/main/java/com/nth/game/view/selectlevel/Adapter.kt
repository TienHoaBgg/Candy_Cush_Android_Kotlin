package com.nth.game.view.selectlevel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nth.game.databinding.ItemLevelBinding
import com.nth.game.model.Level

/**
 * Created by NguyenTienHoa on 1/13/2021
 */

class Adapter:RecyclerView.Adapter<Adapter.ViewHolder> {
    private lateinit var binding: ItemLevelBinding
    private var inter:IAdapter
    constructor(inter:IAdapter){
        this.inter = inter
    }

    class ViewHolder:RecyclerView.ViewHolder {
        val binding:ItemLevelBinding
        constructor(binding: ItemLevelBinding) : super(binding.root){
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemLevelBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        inter.getList()[position].typeView = 0
        holder.binding.item = inter.getList()[position]
        holder.binding.onClickItem.setOnClickListener{
            inter.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return inter.getList().size
    }

    interface IAdapter {
        fun onClick(position: Int)
        fun getList():MutableList<Level>
    }
}




package com.example.maskapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maskapp.databinding.ItemStoreBinding
import com.example.maskapp.model.StoreModel

class MaskAdapter : RecyclerView.Adapter<MaskAdapter.MaskViewHolder>() {
    private var storeList :List<StoreModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemStoreBinding
            .inflate(layoutInflater, parent, false)
            .let { MaskViewHolder(it) }
    }

    override fun onBindViewHolder(holder: MaskViewHolder, position: Int) {
        holder.onBind(storeList, position)
    }

    override fun getItemCount(): Int = storeList.size

    fun refreshData(list : List<StoreModel>) {
        this.storeList = list
        notifyDataSetChanged()
    }

    class MaskViewHolder(private val binding: ItemStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun onBind(storeList: List<StoreModel>, position: Int) {
                binding.textViewName.text = storeList[position].name
                binding.textViewAddress.text = storeList[position].addr
                binding.textViewDistance.text = "1.0km"
                binding.textViewEnough.text = storeList[position].remain_stat
                binding.textViewCount.text = "100개 이상"
            }
    }

}
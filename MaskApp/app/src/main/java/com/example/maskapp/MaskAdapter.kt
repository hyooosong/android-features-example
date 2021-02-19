package com.example.maskapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.maskapp.databinding.ItemStoreBinding
import com.example.maskapp.model.StoreModel

class MaskAdapter : RecyclerView.Adapter<MaskAdapter.MaskViewHolder>() {
    private var storeList: List<StoreModel> = mutableListOf()

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

    fun refreshData(list: List<StoreModel>) {
        this.storeList = list
        notifyDataSetChanged()
    }

    class MaskViewHolder(private val binding: ItemStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var strRemainCount: String = ""
        var strRemainStat: String = ""
        var color: Int = Color.GREEN
        fun onBind(storeList: List<StoreModel>, position: Int) {
            setCountRemainStat(storeList, position)
            binding.textViewName.text = storeList[position].name
            binding.textViewAddress.text = storeList[position].addr
            binding.textViewDistance.text = "1.0km"
            binding.textViewEnough.apply {
                text = strRemainStat
                setTextColor(color)
            }
            binding.textViewCount.apply {
                text = strRemainCount
                setTextColor(color)
            }
        }

        private fun setCountRemainStat(storeList: List<StoreModel>, position: Int){
            when (storeList[position].remain_stat) {
                "plenty" -> {
                    strRemainCount = "100개 이상"
                    strRemainStat = "충분"
                    color = Color.parseColor("#339900")
                }
                "some" -> {
                    strRemainCount = "30개 이상"
                    strRemainStat = "여유"
                    color = Color.parseColor("#FFCC00")
                }
                "few" -> {
                    strRemainCount = "2개 이상"
                    strRemainStat = "매진 임박"
                    color = Color.RED
                }
                "empty" -> {
                    strRemainCount = "1개 이하"
                    strRemainStat = "재고 없음"
                    color = Color.GRAY
                }
                else -> {}
            }
        }
    }

}
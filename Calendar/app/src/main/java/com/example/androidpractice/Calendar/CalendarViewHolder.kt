package com.example.androidpractice.Calendar

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpractice.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_date.view.*

class CalendarViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    var date = itemView.findViewById<TextView>(R.id.textview_date)
}

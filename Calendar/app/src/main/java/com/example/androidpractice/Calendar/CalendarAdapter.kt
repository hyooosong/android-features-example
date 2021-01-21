package com.example.androidpractice.Calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpractice.R
import java.util.*

class CalendarAdapter(val fm: CalendarFragment) : RecyclerView.Adapter<CalendarViewHolder> () {
    val calendar = CalendarView()

    init {
        calendar.initCalendar {
            refreshView(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int = CalendarView.LOW_OF_CALENDAR * CalendarView.DAYS_OF_WEEK

    // 일요일 RED, 나머지 BLACK 설정
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        if (position % CalendarView.DAYS_OF_WEEK == 0)
            holder.date.setTextColor(Color.parseColor("#ff0000"))
        else
            holder.date.setTextColor(Color.parseColor("#000000"))

        // 해당 월 아닐 때 투명도 조정
        if (position < calendar.prevMonthTail
                || position >= calendar.prevMonthTail + calendar.currentMonthMaxDate) {
            holder.date.alpha = 0.3f
        } else {
            holder.date.alpha = 1f
        }
        holder.date.text=calendar.data[position].toString()
    }

    fun changeToPrevMonth() {
        calendar.changeToPrevMonth {
            refreshView(it)
        }
    }

    fun changeToNextMonth() {
        calendar.changeToNextMonth {
            refreshView(it)
        }
    }

    private fun refreshView(calendar: Calendar) {
        notifyDataSetChanged()
        fm.refreshCurrentMonth(calendar)
    }
}
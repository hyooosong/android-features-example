package com.example.androidpractice.Calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidpractice.R
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    private lateinit var caladapter : CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {
        caladapter = CalendarAdapter(this)
        rcv_dateview.apply {
            adapter = caladapter
            layoutManager = GridLayoutManager(view?.context, CalendarView.DAYS_OF_WEEK)
        }
        go_nextMonth.setOnClickListener {
            caladapter.changeToNextMonth()
        }
        go_prevMonth.setOnClickListener {
            caladapter.changeToPrevMonth()
        }
    }

    // calendar header
    fun refreshCurrentMonth(calendar : Calendar) {
        val dateFormat = SimpleDateFormat("yyyy년 MM월", Locale.KOREAN)
        calendar_display.text = dateFormat.format(calendar.time)
    }
}
package com.example.androidpractice.Calendar

import java.util.*

class CalendarView {
    companion object {
        const val DAYS_OF_WEEK = 7
        const val LOW_OF_CALENDAR = 5
    }

    var currentMonthMaxDate = 0
    var nextMonthHead = 0
    var prevMonthTail = 0

    val calendar = Calendar.getInstance()
    var data = arrayListOf<Int>()

    init {
        calendar.time = Date()
    }

    fun initCalendar(callback : (Calendar) -> Unit) {
        makeMonthDate(callback)
    }

    fun changeToPrevMonth(callback: (Calendar) -> Unit) {
        // 1월에서 -> 작년 12월 넘어갈 때
        if(calendar.get(Calendar.MONTH)==0) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)-1)
            calendar.set(Calendar.MONTH, Calendar.DECEMBER)
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1)
        }
        makeMonthDate(callback)
    }

    fun changeToNextMonth(callback: (Calendar) -> Unit) {
        // 12월에서 -> 내년 1월 넘어갈 때
        if(calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)+1)
            calendar.set(Calendar.MONTH,0)
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1)
        }
        makeMonthDate(callback)
    }

    private fun makeMonthDate(callback:(Calendar) -> Unit) {
        data.clear()
        calendar.set(Calendar.DATE, 1)
        currentMonthMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        prevMonthTail = calendar.get(Calendar.DAY_OF_WEEK) - 1
        makePrevMonthTail(calendar.clone() as Calendar)
        makeCurrentMonth(calendar)

        nextMonthHead = LOW_OF_CALENDAR * DAYS_OF_WEEK - (prevMonthTail + currentMonthMaxDate)
        makeNextMonthHead()

        callback(calendar)
    }

    private fun makePrevMonthTail(calendar: Calendar) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1)
        val maxDate = calendar.getActualMaximum(Calendar.DATE)
        var maxOffsetDate = maxDate - prevMonthTail

        for (i in 1..prevMonthTail)
            data.add(++maxOffsetDate)
    }

    private fun makeNextMonthHead() {
        var date = 1
        for(i in 1..nextMonthHead)
            data.add(date++)
    }

    private fun makeCurrentMonth(calendar: Calendar) {
        for (i in 1..calendar.getActualMaximum(Calendar.DATE))
            data.add(i)
    }
}
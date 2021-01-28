package com.example.room

import android.database.Observable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    var editTextTodo = ObservableField<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this

        db = Room.databaseBuilder(baseContext, AppDatabase::class.java, "TodoDB")
            .allowMainThreadQueries()
            .build()

    }

    private fun setTodoUseLiveData() {
        db.toDoDao().getTodoList().observe(this, Observer { todo ->
            binding.textViewTodoList.text = todo.toString()
        })
    }

    fun setTodoList(){
        db.toDoDao().insert(Todo(editTextTodo.get().toString()))
        setTodoUseLiveData()
        editTextTodo.set(" ")
    }
}
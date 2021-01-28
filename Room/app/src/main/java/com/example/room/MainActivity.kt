package com.example.room

import android.database.Observable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
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

    fun setTodoList(){
        db.toDoDao().insert(Todo(editTextTodo.get().toString()))
        binding.textViewTodoList.text = db.toDoDao().getTodoList().toString()
        editTextTodo.set(" ")
    }
}
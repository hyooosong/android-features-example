package com.example.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    var todoList: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
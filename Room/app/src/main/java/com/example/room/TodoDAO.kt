package com.example.room

import androidx.room.*

@Dao
interface TodoDAO {
    @Query("SELECT * FROM Todo")
    fun getAll(): List<Todo>

    @Query("SELECT todoList FROM Todo")
    fun getTodoList(): MutableList<String>

    @Insert
    fun insert(todo: Todo)

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo)
}
package com.egaragul.task_4_room_and_cursor.domain.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Animal(
    @PrimaryKey(autoGenerate = true)
    val _id : Int = 0,
    @ColumnInfo
    val name : String,
    @ColumnInfo
    val age : Int,
    @ColumnInfo
    val breed : String
)
package com.egaragul.task_4_room_and_cursor.domain.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.egaragul.task_4_room_and_cursor.domain.data.Animal

@Database(entities = [Animal::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun animalDao() : AnimalDao
}
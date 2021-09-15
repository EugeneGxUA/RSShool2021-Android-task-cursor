package com.egaragul.task_4_room_and_cursor.domain.db.room

import androidx.room.*
import com.egaragul.task_4_room_and_cursor.domain.data.Animal

@Dao
interface AnimalDao {

    @Query("SELECT * FROM animal")
    fun gelAll() : List<Animal>

    @Query("SELECT _id, name, age, breed FROM animal WHERE _id IN (:userIds)")
    fun loadByIds(userIds: Int): Animal

    @Update
    fun update(animal: Animal)

    @Insert
    fun insert(animal: Animal)

    @Delete
    fun delete(animal: Animal)
}
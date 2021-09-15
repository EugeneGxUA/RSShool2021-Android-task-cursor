package com.egaragul.task_4_room_and_cursor.domain.repository

import androidx.room.Room
import com.egaragul.task_4_room_and_cursor.App
import com.egaragul.task_4_room_and_cursor.domain.data.Animal
import com.egaragul.task_4_room_and_cursor.domain.db.SqlHelper.CursorHelper
import com.egaragul.task_4_room_and_cursor.domain.db.room.RoomDatabase

class AnimalRepository {

    private val db = Room.databaseBuilder(
        App.getAppContext(),
        RoomDatabase::class.java, "AnimalReader.db"
    ).build()

    private val cursorHelper = CursorHelper()

    private var isRoom = true
    fun provideRoomUsing(isCursor : Boolean) {
        isRoom = !isCursor
    }

    fun save(name: String, age: Int, breed: String) {
        if (isRoom) {
            db.animalDao().insert(Animal(0, name, age, breed))
        } else {
            cursorHelper.save(name, age, breed)
        }
    }

    fun read() : List<Animal> {
        return if (isRoom) {
            db.animalDao().gelAll()
        } else {
            cursorHelper.read()
        }
    }

    fun update(animal: Animal) : Boolean {
        return if (isRoom) {
            db.animalDao().update(animal)
            true
        } else {
            cursorHelper.update(animal)
        }
    }

    fun delete(animal: Animal) : Boolean {
        return if (isRoom) {
            db.animalDao().delete(animal)
            true
        } else {
            cursorHelper.delete(animal)
        }
    }

    fun getItemById(id : Int) : Animal? {
        return if (isRoom) {
            db.animalDao().loadByIds(id)
        } else {
            cursorHelper.getItemById(id)
        }
    }

    fun onDestroy() {
        cursorHelper.onDestroy()
        db.close()
    }
}
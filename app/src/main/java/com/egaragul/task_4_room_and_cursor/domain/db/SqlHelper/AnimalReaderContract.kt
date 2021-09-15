package com.egaragul.task_4_room_and_cursor.domain.db.SqlHelper

import android.provider.BaseColumns

object AnimalReaderContract {

    object AnimalEntry : BaseColumns {
        const val TABLE_NAME = "Animal"
        const val COLUMN_NAME = "name"
        const val COLUMN_AGE = "age"
        const val COLUMN_BREED = "breed"
    }
}


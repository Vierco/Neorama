package com.alvarez.sergio.actraining.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NeoEntity::class], version = 1, exportSchema = false)
abstract class NeoDataBase : RoomDatabase() {

    abstract fun neoDao(): NeoDAO
}

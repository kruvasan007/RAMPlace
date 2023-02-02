package ru.cft.shift2023winter.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
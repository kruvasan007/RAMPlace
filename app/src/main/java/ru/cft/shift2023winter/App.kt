package ru.cft.shift2023winter

import android.app.Application
import androidx.room.Room
import ru.cft.shift2023winter.DataBase.AppDatabase


class App : Application() {
    companion object {
        var likeDatabase: AppDatabase? = null
        fun getDatabase(): AppDatabase? {
            return likeDatabase
        }
    }

    override fun onCreate() {
        super.onCreate()
        likeDatabase = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "DB")
            .allowMainThreadQueries()
            .build()
    }

}
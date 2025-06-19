package com.example.metrimonialapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

@Database(
    entities = [UserProfileEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserProfileDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val inst = Room.databaseBuilder<AppDatabase>( // Changed to Room.databaseBuilder
                    context.applicationContext,
                    "matchmate-db"
                ).build()
                INSTANCE = inst
                inst
            }
    }
}

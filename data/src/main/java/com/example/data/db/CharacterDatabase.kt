package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.dto.CharacterDatabaseDto

@Database(
    entities = [CharacterDatabaseDto::class],
    version = 1
)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
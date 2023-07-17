package com.example.data.db

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.data.dto.CharacterDatabaseDto

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character")
    fun getCharacterList(): List<CharacterDatabaseDto>

    @Query("SELECT * FROM character WHERE name = :name")
    fun getCharacter(name: String): List<CharacterDatabaseDto>

    @Insert(onConflict = REPLACE)
    fun insertCharacters(characters: List<CharacterDatabaseDto>)

    @Query("DELETE FROM character")
    fun deleteAllCharacters()
}
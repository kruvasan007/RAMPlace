package ru.cft.shift2023winter.DataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character")
    fun getAll(): List<CharacterDto>

    @Insert
    fun insertCharacter(characterDto: CharacterDto)

    @Delete
    fun delete(characterDto: CharacterDto)
}
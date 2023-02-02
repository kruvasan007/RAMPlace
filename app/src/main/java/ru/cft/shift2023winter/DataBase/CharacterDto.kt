package ru.cft.shift2023winter.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterDto(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "status") var status: String? = null,
    @ColumnInfo(name = "image_url") var image: String? = null,
)
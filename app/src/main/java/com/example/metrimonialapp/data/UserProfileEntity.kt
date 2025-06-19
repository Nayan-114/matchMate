package com.example.metrimonialapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey val uuid: String,
    val fullName: String,
    val age: Int,
    val city: String,
    val state: String,
    val avatarUrl: String,
    val score: Int,
    val selection: Boolean?
)

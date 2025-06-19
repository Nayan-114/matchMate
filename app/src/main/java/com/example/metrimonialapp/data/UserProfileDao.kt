package com.example.metrimonialapp.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profiles")
    fun getAll(): Flow<List<UserProfileEntity>>

    @Query("SELECT * FROM user_profiles WHERE uuid = :id")
    suspend fun getById(id: String): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(users: List<UserProfileEntity>)

    @Update
    suspend fun update(user: UserProfileEntity)
}

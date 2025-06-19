package com.example.metrimonialapp.repository

import com.example.metrimonialapp.data.ApiService
import com.example.metrimonialapp.data.UserProfileDao
import com.example.metrimonialapp.data.UserProfileEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.IOException
import kotlin.math.abs

class UserRepository(
    private val api: ApiService,
    private val dao: UserProfileDao,
    private val referenceAge: Int = 30
) {
    /** Expose the Room flow as before */
    val usersFlow = dao.getAll()

    /** Fetch network data, but merge into DB without duplicating */
    suspend fun fetchAndCacheUsers() {
        // Grab a snapshot of what's already in the DB
        val localMap = dao.getAll().first().associateBy { it.uuid }

        repeat(3) { attempt ->
            try {
                val resp = api.fetchUsers()
                if (resp.isSuccessful) {
                    val remote = resp.body()!!.results
                    val toUpsert = remote.map { u ->
                        val existing = localMap[u.login.uuid]
                        UserProfileEntity(
                            uuid      = u.login.uuid,
                            fullName  = u.name.first,
                            age       = u.dob.age,
                            city      = u.location.city,
                            state     = u.location.state,
                            avatarUrl = u.picture.medium,
                            score     = calculateScore(u.dob.age, referenceAge),
                            // preserve any prior Accept/Decline
                            selection = existing?.selection
                        )
                    }
                    dao.upsertAll(toUpsert)
                }
                return
            } catch (io: IOException) {
                if (attempt < 2) delay(1_000) // back-off then retry
            }
        }
    }
    suspend fun setSelection(uuid: String, accepted: Boolean) {
        dao.getById(uuid)?.let { old ->
            dao.update(old.copy(selection = accepted))
        }
    }

    private fun calculateScore(age: Int, target: Int): Int {
        val diff = abs(age - target)
        return (100 - diff * 2).coerceIn(0, 100)
    }
}

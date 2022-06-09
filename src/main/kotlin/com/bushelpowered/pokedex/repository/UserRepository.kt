package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.ArrayList

@Repository
interface UserRepository : JpaRepository<User, Int>{
    fun findByUsername(username: String?): Optional<User>
    fun findByEmail(email: String?): Optional<User>
    fun existsByEmail(email: String?): Optional<Boolean>

}
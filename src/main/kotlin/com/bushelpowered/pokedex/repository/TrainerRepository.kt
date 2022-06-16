package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.entity.Trainer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TrainerRepository : JpaRepository<Trainer, Int>{
    fun findByUsername(username: String?): Trainer?
    fun findByEmail(email: String?): Trainer?
    fun existsByEmail(email: String?): Boolean

}
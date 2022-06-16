package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.entity.Types
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TypeRepository : JpaRepository<Types, Int> {
    fun findByType(type: String): Types
}
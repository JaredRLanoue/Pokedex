package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.entity.Type
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TypeRepository : JpaRepository<Type, Int> {
    fun findByType(type: String): Type
}
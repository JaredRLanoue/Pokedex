package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.model.Types
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TypeRepository : JpaRepository<Types, Int> {
}
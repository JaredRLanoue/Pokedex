package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.model.Abilities
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AbilitiesRepository : JpaRepository<Abilities, Int> {
}
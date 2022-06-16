package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.entity.Abilities
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AbilitiesRepository : JpaRepository<Abilities, Int> {
    fun findByAbility(ability: String): Abilities
}
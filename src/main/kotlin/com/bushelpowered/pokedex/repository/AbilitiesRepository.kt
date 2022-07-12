package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.entity.Ability
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AbilitiesRepository : JpaRepository<Ability, Int> {
    fun findByAbility(ability: String): Ability
}
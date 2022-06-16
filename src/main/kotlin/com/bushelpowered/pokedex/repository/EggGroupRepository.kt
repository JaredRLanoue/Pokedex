package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.entity.EggGroups
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EggGroupRepository : JpaRepository<EggGroups, Int> {
    fun findByEggGroup(eggGroup: String): EggGroups
}
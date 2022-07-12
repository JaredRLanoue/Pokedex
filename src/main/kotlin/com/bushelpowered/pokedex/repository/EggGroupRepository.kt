package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.entity.EggGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EggGroupRepository : JpaRepository<EggGroup, Int> {
    fun findByEggGroup(eggGroup: String): EggGroup
}
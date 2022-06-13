package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.model.EggGroups
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EggGroupRepository : JpaRepository<EggGroups, Int> {
}
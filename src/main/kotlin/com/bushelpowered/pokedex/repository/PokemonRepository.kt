package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.model.Pokemon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PokemonRepository : JpaRepository<Pokemon, Int>
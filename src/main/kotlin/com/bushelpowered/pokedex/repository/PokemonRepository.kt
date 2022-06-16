package com.bushelpowered.pokedex.repository

import com.bushelpowered.pokedex.entity.Pokemon
import org.springframework.data.repository.PagingAndSortingRepository

interface PokemonRepository : PagingAndSortingRepository<Pokemon, Int> {
    fun getReferenceById(pokemonID: Int): Pokemon
}
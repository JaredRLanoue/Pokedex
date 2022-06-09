package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.repository.PokemonRepository
import com.bushelpowered.pokedex.model.Pokemon
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PokemonService(val pokemonRepository: PokemonRepository) {

    fun getAllPokemon(): List<Pokemon> {
        return pokemonRepository.findAll()
    }

    fun getPokemonByID(pokemon_id: Int): Pokemon? {
        return pokemonRepository.findByIdOrNull(pokemon_id)
    }
}
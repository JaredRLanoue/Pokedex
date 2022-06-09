package com.bushelpowered.pokedex.controller

import com.bushelpowered.pokedex.exception.PokemonNotFoundException
import com.bushelpowered.pokedex.model.Pokemon
import com.bushelpowered.pokedex.service.PokemonService
import liquibase.change.core.LoadDataColumnConfig
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
class PokemonController(val service: PokemonService) {

    @GetMapping("/pokemon")
    fun pokemonIndex(): List<Pokemon> = service.getAllPokemon()

    @GetMapping("/pokemon/{pokemonID}")
    fun searchPokemonByID(@PathVariable pokemonID: Int): Pokemon? {
        if (pokemonID > service.getAllPokemon().size || pokemonID < 0) {
            throw (PokemonNotFoundException("Pokemon with the ID of $pokemonID does not exist"))
        }
        return service.getPokemonByID(pokemonID)
    }
}



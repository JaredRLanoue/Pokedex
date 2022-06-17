package com.bushelpowered.pokedex.controller

import com.bushelpowered.pokedex.dto.MessageDTO
import com.bushelpowered.pokedex.service.DatabaseService
import com.bushelpowered.pokedex.service.PokemonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class PokemonController(private val pokemonService: PokemonService, private val databaseService: DatabaseService) {

    @GetMapping("/pokemon")
    fun getAllPokemon(@RequestParam(required = false, defaultValue = "0") page: Int): ResponseEntity<Any> {
        return ResponseEntity.accepted().body(pokemonService.getAllPokemon(page))
    }

    @GetMapping("/pokemon/{pokemonID}")
    fun getPokemonByID(@PathVariable pokemonID: Int): ResponseEntity<Any> {
        return if (pokemonService.checkIfPokemonExists(pokemonID)) {
            ResponseEntity.accepted().body(pokemonService.getPokemonByID(pokemonID))
        } else {
            ResponseEntity.badRequest().body(MessageDTO("Pokemon with the ID of $pokemonID does not exist!"))
        }
    }

    @PostMapping("/upload")
    fun loadPokemonIntoDatabase(): ResponseEntity<MessageDTO> {
        return databaseService.setupDatabase()
    }
}



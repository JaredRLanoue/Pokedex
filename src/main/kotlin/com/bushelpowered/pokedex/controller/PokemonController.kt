package com.bushelpowered.pokedex.controller

import com.bushelpowered.pokedex.dto.MessageDTO
import com.bushelpowered.pokedex.service.DatabaseService
import com.bushelpowered.pokedex.service.PokemonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
// Good use of private vals
// Break this into multiple lines, with one dependency on each line for readability
class PokemonController(
    private val pokemonService: PokemonService,
    private val databaseService: DatabaseService
) {

    @GetMapping("/pokemon")
    fun getAllPokemon(@RequestParam(required = false, defaultValue = "0") page: Int): ResponseEntity<PageDTO> {

        // getAllPokemon name isn't representative of what this function is actually doing
        // Renaming to something like `getPokemonByPage` instead
        // Don't use Any - instead denote the response object as the generic type for the ResponseEntity
        // There isn't a way to set the amount of records per page

        return ResponseEntity.accepted().body(pokemonService.getAllPokemon(page))
    }

    @GetMapping("/pokemon/{pokemonID}")
    fun getPokemonByID(@PathVariable pokemonID: Int): ResponseEntity<Any> {

        // When statements are sometimes more readable that if/else
        // Refactor this to be something like:
            /**
            
            **/

        // Your controllers should be slim. This logic should be moved to the service layer

        // return when (pokemonService.checkIfPokemonExists(pokemonID)) {
        //     true -> ResponseEntity.accepted().body(pokemonService.getPokemonByID(pokemonID))
        //     else -> ResponseEntity.badRequest().body(MessageDTO("Pokemon with the ID of $pokemonID does not exist!"))
        // }

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



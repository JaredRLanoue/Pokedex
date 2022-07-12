package com.bushelpowered.pokedex.controller

import com.bushelpowered.pokedex.dto.response.MessageResponse
import com.bushelpowered.pokedex.dto.response.PageResponse
import com.bushelpowered.pokedex.dto.response.PokemonResponse
import com.bushelpowered.pokedex.service.DatabaseService
import com.bushelpowered.pokedex.service.PokemonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class PokemonController(
    private val pokemonService: PokemonService,
    private val databaseService: DatabaseService
) {

    @GetMapping("/pokemon")
    fun getPokemonByPage(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "15") size: Int
    ): ResponseEntity<PageResponse> {
        return pokemonService.getPokemonByPage(page, size)
    }

    @GetMapping("/pokemon/{pokemonID}")
    fun getPokemonByID(@PathVariable pokemonID: Int): ResponseEntity<PokemonResponse> {
        return pokemonService.getPokemonByID(pokemonID)
    }

    @PostMapping("/upload")
    fun loadPokemonIntoDatabase(): ResponseEntity<MessageResponse> {
        return databaseService.setupDatabase()
    }
}



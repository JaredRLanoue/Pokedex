package com.bushelpowered.pokedex.controller

import com.bushelpowered.pokedex.model.*
import com.bushelpowered.pokedex.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class UserController(val service: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody user: User): ResponseEntity<Message> {
        return service.checkValidRegistration(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody login: Login): Any {
        return service.checkLogin(login)
    }

    @GetMapping("/{userID}/pokemon")
    fun getCapturedPokemon(@PathVariable userID: Int): Any {
        return service.getCapturedPokemon(userID)
    }

    @PutMapping("/{userID}/pokemon/capture/{pokemonID}")
    fun capturePokemons(@PathVariable userID: Int, @PathVariable pokemonID: Int): User {
        return service.capturePokemon(userID, pokemonID)
    }

    @PutMapping("/{userID}/pokemon/release/{pokemonID}")
    fun releasePokemons(@PathVariable userID: Int, @PathVariable pokemonID: Int): User {
        return service.releasePokemon(userID, pokemonID)
    }
}
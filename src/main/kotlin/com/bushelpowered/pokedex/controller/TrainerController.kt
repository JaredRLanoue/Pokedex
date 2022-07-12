package com.bushelpowered.pokedex.controller

import com.bushelpowered.pokedex.dto.request.TrainerLoginRequest
import com.bushelpowered.pokedex.dto.request.TrainerRegistrationRequest
import com.bushelpowered.pokedex.dto.response.MessageResponse
import com.bushelpowered.pokedex.service.CaptureService
import com.bushelpowered.pokedex.service.TrainerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TrainerController(
    private val trainerService: TrainerService,
    private val capturedService: CaptureService
) {

    @PostMapping("/register")
    fun register(@RequestBody(required = true) trainer: TrainerRegistrationRequest): ResponseEntity<MessageResponse> {
        return trainerService.checkValidRegistration(trainer)
    }

    @PostMapping("/login")
    fun login(@RequestBody trainer: TrainerLoginRequest): ResponseEntity<MessageResponse> {
        return trainerService.checkLogin(trainer)
    }

    @GetMapping("/{trainerID}/pokemon")
    fun getCapturedPokemon(@PathVariable trainerID: Int): ResponseEntity<Any> {
        return capturedService.getCapturedPokemon(trainerID)
    }

    @PutMapping("/{trainerID}/pokemon/capture/{pokemonID}")
    fun capturePokemons(@PathVariable trainerID: Int, @PathVariable pokemonID: Int): ResponseEntity<MessageResponse> {
        return capturedService.capturePokemon(trainerID, pokemonID)
    }

    @PutMapping("/{trainerID}/pokemon/release/{pokemonID}")
    fun releasePokemons(@PathVariable trainerID: Int, @PathVariable pokemonID: Int): ResponseEntity<MessageResponse> {
        return capturedService.releasePokemon(trainerID, pokemonID)
    }
}
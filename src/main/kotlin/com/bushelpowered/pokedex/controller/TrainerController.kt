package com.bushelpowered.pokedex.controller

import com.bushelpowered.pokedex.dto.MessageDTO
import com.bushelpowered.pokedex.dto.TrainerLoginDTO
import com.bushelpowered.pokedex.dto.TrainerRegistrationDTO
import com.bushelpowered.pokedex.service.CaptureService
import com.bushelpowered.pokedex.service.TrainerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TrainerController(
    private val trainerService: TrainerService,
    private val capturedService: CaptureService, ) {

    @PostMapping("/register")
    fun register(@RequestBody trainer: TrainerRegistrationDTO): ResponseEntity<Any> {
        return if(trainer.email.isNullOrBlank() || trainer.password.isNullOrBlank() || trainer.username.isNullOrBlank()){
            ResponseEntity.badRequest().body(MessageDTO("Missing request body parameter"))
        } else {
            trainerService.checkValidRegistration(trainer)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody trainer: TrainerLoginDTO): ResponseEntity<MessageDTO> {
        return if(trainer.email.isNullOrBlank() || trainer.password.isNullOrBlank()) {
            ResponseEntity.badRequest().body(MessageDTO("Missing request body parameter"))
        } else {
            trainerService.checkLogin(trainer)
        }
    }

    @GetMapping("/{trainerID}/pokemon")
    fun getCapturedPokemon(@PathVariable trainerID: Int): ResponseEntity<Any> {
        return if (trainerService.checkIfUserExists(trainerID)) {
            capturedService.getCapturedPokemon(trainerID)
        } else {
            ResponseEntity.badRequest().body(MessageDTO("Trainer does not exist"))
        }
    }

    @PutMapping("/{trainerID}/pokemon/capture/{pokemonID}")
    fun capturePokemons(@PathVariable trainerID: Int, @PathVariable pokemonID: Int): ResponseEntity<Any> {
        return capturedService.capturePokemon(trainerID, pokemonID)
    }

    @PutMapping("/{trainerID}/pokemon/release/{pokemonID}")
    fun releasePokemons(@PathVariable trainerID: Int, @PathVariable pokemonID: Int): ResponseEntity<Any> {
        return capturedService.releasePokemon(trainerID, pokemonID)
    }
}
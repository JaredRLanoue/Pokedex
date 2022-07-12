package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.dto.response.MessageResponse
import com.bushelpowered.pokedex.repository.PokemonRepository
import com.bushelpowered.pokedex.repository.TrainerRepository
import com.bushelpowered.pokedex.util.toTrainerDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CaptureService(
    private val trainerRepository: TrainerRepository,
    private val pokemonRepository: PokemonRepository,
) {

    fun capturePokemon(trainerID: Int, pokemonID: Int): ResponseEntity<MessageResponse> {
        return if (trainerRepository.existsById(trainerID) && pokemonRepository.existsById(pokemonID)) {
            val trainer = trainerRepository.getReferenceById(trainerID)
            val pokemonToCapture = pokemonRepository.getReferenceById(pokemonID)

            trainer.capturedPokemon = trainer.capturedPokemon + pokemonToCapture
            trainerRepository.save(trainer)
            ResponseEntity(MessageResponse("${pokemonToCapture.name} has been captured!"), HttpStatus.OK)
        } else {
            throw IllegalArgumentException("Trainer or Pokemon does not exist")
        }
    }

    fun releasePokemon(trainerID: Int, pokemonID: Int): ResponseEntity<MessageResponse> {
        if (trainerRepository.existsById(trainerID) && pokemonRepository.existsById(pokemonID)) {
            val trainer = trainerRepository.getReferenceById(trainerID)
            val pokemonToRelease = pokemonRepository.getReferenceById(pokemonID)

            if (pokemonToRelease !in trainer.capturedPokemon) {
                throw IllegalArgumentException("${pokemonToRelease.name} does not exist in trainer's captured list")
            }
            trainer.capturedPokemon = trainer.capturedPokemon - pokemonToRelease
            trainerRepository.save(trainer)
            return ResponseEntity(
                MessageResponse("${pokemonToRelease.name} has has been released!"),
                HttpStatus.OK
            )
        } else {
            throw IllegalArgumentException("Trainer or Pokemon does not exist")
        }
    }

    fun getCapturedPokemon(trainerID: Int): ResponseEntity<Any> {
        return if (trainerRepository.existsById(trainerID)) {
            val trainer = trainerRepository.getReferenceById(trainerID)

            if (trainer.capturedPokemon.isEmpty()) {
                throw IllegalArgumentException("No pokemon have been captured yet")
            } else {
                ResponseEntity.accepted().body(trainer.toTrainerDto())
            }
        } else {
            throw IllegalArgumentException("Trainer with the ID $trainerID does not exist yet")
        }
    }
}
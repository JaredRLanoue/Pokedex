package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.dto.MessageDTO
import com.bushelpowered.pokedex.repository.PokemonRepository
import com.bushelpowered.pokedex.repository.TrainerRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CaptureService(
    private val trainerRepository: TrainerRepository,
    private val pokemonRepository: PokemonRepository,
    private val modelMappingService: ConverterService
) {

    fun capturePokemon(trainerID: Int, pokemonID: Int): ResponseEntity<Any> {
        return if (trainerRepository.existsById(trainerID) && pokemonRepository.existsById(pokemonID)) {
            val user = trainerRepository.getReferenceById(trainerID)
            val pokemonToCapture = pokemonRepository.getReferenceById(pokemonID)

            user.capturedPokemon = user.capturedPokemon!! + pokemonToCapture
            trainerRepository.save(user)
            ResponseEntity.accepted().body(MessageDTO("${pokemonToCapture.name} has been captured!"))
        } else {
            ResponseEntity.badRequest().body(MessageDTO("Trainer or Pokemon does not exist"))
        }
    }

    fun releasePokemon(trainerID: Int, pokemonID: Int): ResponseEntity<Any> {
        if (trainerRepository.existsById(trainerID) && pokemonRepository.existsById(pokemonID)) {
            val user = trainerRepository.getReferenceById(trainerID)
            val pokemonToRelease = pokemonRepository.getReferenceById(pokemonID)

            if (user.capturedPokemon?.contains(pokemonToRelease) == false) {
                return ResponseEntity.badRequest()
                    .body(MessageDTO("${pokemonToRelease.name} does not exist in trainer's captured list"))
            }
            user.capturedPokemon = user.capturedPokemon!! - pokemonToRelease
            trainerRepository.save(user)
            return ResponseEntity.accepted().body(MessageDTO("${pokemonToRelease.name} has has been released!"))
        } else {
            return ResponseEntity.badRequest().body(MessageDTO("Trainer or Pokemon does not exist"))
        }
    }

    fun getCapturedPokemon(trainerID: Int): ResponseEntity<Any> {
        if (trainerRepository.existsById(trainerID)) {
            val trainer = trainerRepository.getReferenceById(trainerID)

            return if (trainer.capturedPokemon.isNullOrEmpty()) {
                return ResponseEntity.badRequest().body(MessageDTO("No pokemon have been captured yet"))
            } else {
                ResponseEntity.accepted().body(modelMappingService.trainerEntityToDTO(trainer))
            }
        } else {
            return ResponseEntity.badRequest().body(MessageDTO("Trainer does not exist yet, try creating one first"))
        }
    }
}
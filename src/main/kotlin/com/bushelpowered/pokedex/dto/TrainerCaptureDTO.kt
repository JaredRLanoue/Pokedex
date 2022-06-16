package com.bushelpowered.pokedex.dto

data class TrainerCaptureDTO(
    val username: String,
    val email: String,
    var capturedPokemon: List<PokemonDTO>?
)
package com.bushelpowered.pokedex.dto.response

data class TrainerCaptureResponse(
    val username: String,
    val email: String,
    var capturedPokemon: List<PokemonResponse>?
)
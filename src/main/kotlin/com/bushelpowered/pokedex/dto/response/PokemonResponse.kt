package com.bushelpowered.pokedex.dto.response

data class PokemonResponse(
    val id: Int,
    val name: String,
    val types: List<String>,
    val height: Int,
    val weight: Int,
    val abilities: List<String>,
    val eggGroups: List<String>,
    val stats: StatsResponse,
    val genus: String,
    val description: String
)

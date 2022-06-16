package com.bushelpowered.pokedex.dto

data class PokemonDTO(
    val id: Int,
    val name: String,
    val types: List<String>,
    val height: Int,
    val weight: Int,
    val abilities: List<String>,
    val eggGroups: List<String>,
    val stats: StatsDTO,
    val genus: String,
    val description: String
)

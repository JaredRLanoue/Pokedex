package com.bushelpowered.pokedex.dto

data class CustomPageDTO(
    val data: List<PokemonDTO>,
    val meta: MetaDataDTO
) {
    data class MetaDataDTO(
        val currentPage: Int,
        val lastPage: Int,
        val PokemonPerPage: Int,
        val totalPokemon: Int,
        val nextPage: String?,
        val previousPage: String?
    )
}
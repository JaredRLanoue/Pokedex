package com.bushelpowered.pokedex.dto

data class PageDTO(
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
// Instead of using the DTO suffix, you can suffix these DTOs with Response
// Example, PageResponse instead of PageDTO
// Spring has functionality to handle pagination
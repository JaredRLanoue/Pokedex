package com.bushelpowered.pokedex.dto.response

data class PageResponse(
    val data: List<PokemonResponse>,
    val meta: MetaData
) {
    data class MetaData(
        val currentPage: Int,
        val lastPage: Int,
        val PokemonPerPage: Int,
        val totalPokemon: Int,
        val nextPage: String?,
        val previousPage: String?
    )
}
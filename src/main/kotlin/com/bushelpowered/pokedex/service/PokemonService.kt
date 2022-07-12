package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.dto.response.PageResponse
import com.bushelpowered.pokedex.dto.response.PokemonResponse
import com.bushelpowered.pokedex.repository.PokemonRepository
import com.bushelpowered.pokedex.util.toPokemonDto
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@Service
class PokemonService(
    private val pokemonRepository: PokemonRepository
) {

    fun getPokemonByPage(page: Int, size: Int): ResponseEntity<PageResponse> {
        val pageRequest = PageRequest.of(page, size)
        val pageContent = pokemonRepository.findAll(pageRequest)
        val pageContentAsDTO = pageContent.content.map { it.toPokemonDto() }
        val requestURL = ServletUriComponentsBuilder
            .fromCurrentRequestUri()
            .queryParam("page", "")
            .build()
            .toUri()
            .toString()

        return ResponseEntity(
            PageResponse(
                pageContentAsDTO, PageResponse.MetaData(
                    currentPage = pageRequest.pageNumber,
                    lastPage = pageContent.totalPages - 1,
                    PokemonPerPage = pageRequest.pageSize,
                    totalPokemon = pokemonRepository.findAll(Pageable.unpaged()).numberOfElements,
                    nextPage = getNextPage(pageRequest.pageNumber, pageContent.totalPages - 1, requestURL),
                    previousPage = getPreviousPage(pageRequest.pageNumber, requestURL)
                )
            ), HttpStatus.OK
        )
    }

    fun getPokemonByID(pokemonID: Int): ResponseEntity<PokemonResponse> {
        return when (pokemonRepository.existsById(pokemonID)) {
            true -> ResponseEntity(pokemonRepository.getReferenceById(pokemonID).toPokemonDto(), HttpStatus.OK)
            else -> throw IllegalArgumentException("Pokemon with the ID of $pokemonID does not exist.")
        }
    }

    fun getPreviousPage(page: Int, requestURL: String): String? {
        return when (page > 0) {
            true -> requestURL + (page - 1)
            else -> null
        }
    }

    fun getNextPage(page: Int, maxPage: Int, requestURL: String): String? {
        return when (page < maxPage) {
            true -> requestURL + (page + 1)
            else -> null
        }
    }
}


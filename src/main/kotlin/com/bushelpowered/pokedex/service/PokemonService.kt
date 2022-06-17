package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.dto.PageDTO
import com.bushelpowered.pokedex.dto.PokemonDTO
import com.bushelpowered.pokedex.repository.PokemonRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PokemonService(private val converterService: ConverterService, private val pokemonRepository: PokemonRepository) {

    fun checkIfPokemonExists(pokemonID: Int): Boolean {
        return pokemonRepository.existsById(pokemonID)
    }

    fun getAllPokemon(page: Int): PageDTO {
        val pageRequest = PageRequest.of(page, 15)
        val pageContent = pokemonRepository.findAll(pageRequest)
        val pageContentAsDTO = pageContent.content.map { converterService.pokemonEntityToDTO(it) }
        val requestURL = "http://localhost:8080/pokemon?page="

        return PageDTO(
            pageContentAsDTO, PageDTO.MetaDataDTO(
                currentPage = pageRequest.pageNumber,
                lastPage = pageContent.totalPages - 1,
                PokemonPerPage = pageRequest.pageSize,
                totalPokemon = pokemonRepository.findAll(Pageable.unpaged()).numberOfElements,
                nextPage = getNextPage(pageRequest.pageNumber, requestURL),
                previousPage = getPreviousPage(pageRequest.pageNumber, requestURL)
            )
        )
    }

    fun getPokemonByID(pokemonID: Int): PokemonDTO {
        val pokemonEntity = pokemonRepository.findByIdOrNull(pokemonID)
        return converterService.pokemonEntityToDTO(pokemonEntity!!)
    }

    fun getPreviousPage(page: Int, requestURL: String): String? {
        return if (page > 0)
            requestURL + (page - 1)
        else
            null
    }

    fun getNextPage(page: Int, requestURL: String): String? {
        return if (page < 36)
            requestURL + (page + 1)
        else
            null
    }
}


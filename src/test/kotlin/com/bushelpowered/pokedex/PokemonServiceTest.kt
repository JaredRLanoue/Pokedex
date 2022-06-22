package com.bushelpowered.pokedex

import com.bushelpowered.pokedex.repository.PokemonRepository
import com.bushelpowered.pokedex.service.ConverterService
import com.bushelpowered.pokedex.service.PokemonService
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PokemonServiceTest {

    private val pokemonRepository: PokemonRepository = mockk()
    private val converterService: ConverterService = mockk()

    val pokemonService: PokemonService = PokemonService(converterService, pokemonRepository)

    @Test
    fun `getNextPage returns null when on last page`(){
        val results = pokemonService.getNextPage(36, "http://localhost:8080/pokemon?page=")
        assertEquals(null, results)
        // You can use assertNull here
    }

    @Test
    fun `getPreviousPage returns 5 when on page 6`(){
        val results = pokemonService.getPreviousPage(6, "http://localhost:8080/pokemon?page=")
        assertEquals(results, "http://localhost:8080/pokemon?page=5")
    }
}

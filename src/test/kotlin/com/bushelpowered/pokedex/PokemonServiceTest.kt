package com.bushelpowered.pokedex

import com.bushelpowered.pokedex.repository.PokemonRepository
import com.bushelpowered.pokedex.service.PokemonService
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PokemonServiceTest {
    private val pokemonRepository: PokemonRepository = mockk(relaxed = true)
    private val pokemonService: PokemonService = PokemonService(pokemonRepository)

    @Test
    fun `getNextPage returns null when on last page`() {
        val results = pokemonService.getNextPage(36, 36, "http://localhost:8080/pokemon?page=")
        assertNull(results)
    }

    @Test
    fun `getPreviousPage returns 5 when on page 6`() {
        val results = pokemonService.getPreviousPage(6, "http://localhost:8080/pokemon?page=")
        assertEquals(results, "http://localhost:8080/pokemon?page=5")
    }
}

package com.bushelpowered.pokedex.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class PokedexExceptionHandler {

////    @ExceptionHandler
////    fun handleException(exc: PokemonNotFoundException): ResponseEntity<PokemonErrorResponse> {
////        val pokemonErrorResponse = PokemonErrorResponse(
////            HttpStatus.NOT_FOUND.value(), exc.message.toString(), System.currentTimeMillis())
////
////        return ResponseEntity(pokemonErrorResponse, HttpStatus.NOT_FOUND)
////    }

    @ExceptionHandler
    fun handleExceptions(exception: Exception): ResponseEntity<PokedexErrorResponse> {
        val pokedexErrorResponse = PokedexErrorResponse(
            HttpStatus.BAD_REQUEST.value(), exception.message.toString(), System.currentTimeMillis())

        return ResponseEntity(pokedexErrorResponse, HttpStatus.BAD_REQUEST)
    }
}
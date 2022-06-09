package com.bushelpowered.pokedex.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class PokemonExceptionHandler {
    @ExceptionHandler
    fun handleException(exc: PokemonNotFoundException): ResponseEntity<PokemonErrorResponse> {
        val pokemonErrorResponse = PokemonErrorResponse(
            HttpStatus.NOT_FOUND.value(), exc.message.toString(), System.currentTimeMillis())

        return ResponseEntity(pokemonErrorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleOtherExceptions(exception: Exception): ResponseEntity<PokemonErrorResponse> {
        val pokemonErrorResponse = PokemonErrorResponse(
            HttpStatus.BAD_REQUEST.value(), exception.message.toString(), System.currentTimeMillis())

        return ResponseEntity(pokemonErrorResponse, HttpStatus.BAD_REQUEST)
    }
}
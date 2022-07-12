package com.bushelpowered.pokedex.exception

import com.bushelpowered.pokedex.dto.response.MessageResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.security.sasl.AuthenticationException

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationExceptions(exception: AuthenticationException): ResponseEntity<MessageResponse> {
        return ResponseEntity(MessageResponse("Login failed: ${exception.message.toString()}"), HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleArgumentExceptions(exception: IllegalArgumentException): ResponseEntity<MessageResponse> {
        return ResponseEntity(MessageResponse(exception.message.toString()), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpException(exception: HttpMessageNotReadableException): ResponseEntity<MessageResponse> {
        return ResponseEntity(MessageResponse("Missing request body parameters"), HttpStatus.BAD_REQUEST)
    }
}
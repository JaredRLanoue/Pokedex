package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.dto.request.TrainerLoginRequest
import com.bushelpowered.pokedex.dto.request.TrainerRegistrationRequest
import com.bushelpowered.pokedex.dto.response.MessageResponse
import com.bushelpowered.pokedex.repository.TrainerRepository
import com.bushelpowered.pokedex.util.toTrainerEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.regex.Pattern
import javax.security.sasl.AuthenticationException


@Service
class TrainerService(
    private val trainerRepository: TrainerRepository
) {

    fun checkValidRegistration(trainer: TrainerRegistrationRequest): ResponseEntity<MessageResponse> {
        val emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
        val hashingLimit = 72
        val passwordMinimum = 8

        return if (!emailPattern.matcher(trainer.email).matches()) {
            throw IllegalArgumentException("Invalid email")
        } else if (trainer.password.length < passwordMinimum || trainer.password.length > hashingLimit || (trainer.password.count(
                Char::isDigit
            ) < 2)
        ) {
            throw IllegalArgumentException("Password requirements not met, must have $passwordMinimum-$hashingLimit characters and contain two numbers")
        } else if (trainerRepository.findByUsername(trainer.username) != null) {
            throw IllegalArgumentException("Username already exists")
        } else if (trainerRepository.findByEmail(trainer.email) != null) {
            throw IllegalArgumentException("Email already exists")
        } else createUser(trainer)
    }

    fun createUser(trainer: TrainerRegistrationRequest): ResponseEntity<MessageResponse> {
        trainer.password = BCryptPasswordEncoder().encode(trainer.password)
        val trainerEntity = trainer.toTrainerEntity()

        trainerRepository.save(trainerEntity)
        return ResponseEntity(MessageResponse("${trainer.username}'s account has been created"), HttpStatus.CREATED)
    }

    fun checkLogin(trainerLogin: TrainerLoginRequest): ResponseEntity<MessageResponse> {
        val hashingLimit = 72

        if (trainerRepository.existsByEmail(trainerLogin.email)) {
            if (trainerLogin.password.length > hashingLimit) {
                throw AuthenticationException("Password exceeds character limit")
            }
            val passwordMatches = BCryptPasswordEncoder().matches(
                trainerLogin.password,
                trainerRepository.findByEmail(trainerLogin.email)?.password
            )

            return if (passwordMatches) {
                ResponseEntity(MessageResponse("Passwords match for user, login is allowed"), HttpStatus.OK)
            } else {
                throw AuthenticationException("Invalid email or password")
            }
        } else {
            throw AuthenticationException("Invalid email or password")
        }
    }
}
package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.dto.MessageDTO
import com.bushelpowered.pokedex.dto.TrainerLoginDTO
import com.bushelpowered.pokedex.dto.TrainerRegistrationDTO
import com.bushelpowered.pokedex.repository.TrainerRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.regex.Pattern


@Service
class TrainerService(private val trainerRepository: TrainerRepository, private val converterService: ConverterService) {

    fun checkIfUserExists(userId: Int): Boolean {
        return trainerRepository.existsById(userId)
    }

    fun checkValidRegistration(trainer: TrainerRegistrationDTO): ResponseEntity<Any> {
        val emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
        val hashingLimit = 72
        val passwordMinimum = 8

        return if (!emailPattern.matcher(trainer.email.toString()).matches()) {
            ResponseEntity.badRequest().body(MessageDTO("Invalid email"))
        } else if (trainer.password!!.length < passwordMinimum || trainer.password!!.length > hashingLimit || (trainer.password!!.count(Char::isDigit) < 2)) {
            ResponseEntity.badRequest().body(MessageDTO("Password requirements not met, must have $passwordMinimum-$hashingLimit characters and contain two numbers"))
        } else if (trainerRepository.findByUsername(trainer.username) != null) {
            ResponseEntity.badRequest().body(MessageDTO("Username already exists"))
        } else if (trainerRepository.findByEmail(trainer.email) != null) {
            ResponseEntity.badRequest().body(MessageDTO("Email already exists"))
        } else createUser(trainer)
    }

    fun createUser(trainer: TrainerRegistrationDTO): ResponseEntity<Any> {
        trainer.password = BCryptPasswordEncoder().encode(trainer.password)
        val trainerEntity = converterService.trainerDTOToEntity(trainer)

        trainerRepository.save(trainerEntity)
        return ResponseEntity.accepted().body(MessageDTO("${trainer.username}'s account has been created"))
    }

    fun checkLogin(trainerLogin: TrainerLoginDTO): ResponseEntity<MessageDTO> {
        val hashingLimit = 72

        if (trainerRepository.existsByEmail(trainerLogin.email)) {
            if (trainerLogin.password!!.length > hashingLimit) {
                return ResponseEntity.badRequest().body(MessageDTO("Password exceeds character limit"))
            }
            val passwordMatches = BCryptPasswordEncoder().matches(trainerLogin.password, trainerRepository.findByEmail(trainerLogin.email)?.password)

            return if (passwordMatches) {
                ResponseEntity.accepted().body(MessageDTO("Passwords match for user, login is allowed"))
            } else {
                ResponseEntity.badRequest().body(MessageDTO("Invalid email or password"))
            }
        } else {
            return ResponseEntity.badRequest().body(MessageDTO("Invalid email or password"))
        }
    }
}
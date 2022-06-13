package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.model.*
import com.bushelpowered.pokedex.repository.PokemonRepository
import com.bushelpowered.pokedex.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.regex.Pattern


@Service
class UserService(val userRepository: UserRepository, val pokemonRepository: PokemonRepository) {

    fun checkValidRegistration(user: User): ResponseEntity<Message> {
        val pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

        if (!pattern.matcher(user.email).matches())
            throw Exception("Invalid email!")
        else if (userRepository.findByUsername(user.username).isPresent)
            throw Exception("Username already exists!")
        else if (userRepository.findByEmail(user.email).isPresent)
            throw Exception("Email already exists!")
        else return createUser(user)
    }

    fun createUser(user: User): ResponseEntity<Message> {
        user.password = BCryptPasswordEncoder().encode(user.password)
        userRepository.save(user)

        return ResponseEntity.accepted().body(Message("Hello ${user.username}! Trainer has been successfully created!"))
    }

    fun checkLogin(login: Login): Any {
        return if (userRepository.existsByEmail(login.email).get()) {
            val validLogin =
                BCryptPasswordEncoder().matches(login.password, userRepository.findByEmail(login.email).get().password)

            if (validLogin) {
                ResponseEntity.accepted().body(Message("Passwords match for user, login is allowed!"))

            } else {
                throw Exception("Invalid email or password, please try again!")

            }
        } else {
            throw Exception("Invalid email or password, please try again!")
        }
    }

    fun capturePokemon(userID: Int, pokemonID: Int): User {
        if (userRepository.existsById(userID) && pokemonRepository.existsById(pokemonID)) {
            val user = userRepository.getReferenceById(userID)
            val capturedPokemonObject = pokemonRepository.getReferenceById(pokemonID)
            user.capturedPokemon = user.capturedPokemon!! + capturedPokemonObject
            return userRepository.save(user)
        } else {
            throw Exception("Trainer or Pokemon does not exist!")
        }
    }

    fun releasePokemon(userID: Int, pokemonID: Int): User {
        if (userRepository.existsById(userID) && pokemonRepository.existsById(pokemonID)) {
            val user = userRepository.getReferenceById(userID)
            val releasedPokemonObject = pokemonRepository.getReferenceById(pokemonID)
            user.capturedPokemon = user.capturedPokemon!! - releasedPokemonObject
            return userRepository.save(user)
        } else {
            throw Exception("Trainer or Pokemon does not exist!")
        }
    }

    fun getCapturedPokemon(userID: Int): Any {
        if (userRepository.existsById(userID)) {
            val objectList: MutableList<Pokemon> = mutableListOf()
            for (element in userRepository.getReferenceById(userID).capturedPokemon!!)
                objectList += pokemonRepository.getReferenceById(element.id)
            return if (objectList.isEmpty()) {
                throw Exception("No pokemon have been captured yet!")
            } else {
                objectList
            }
        } else {
            throw Exception("Trainer does not exist yet, try creating one first!")
        }
    }
}
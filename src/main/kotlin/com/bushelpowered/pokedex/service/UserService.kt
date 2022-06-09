package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.bCryptPasswordEncoder
import com.bushelpowered.pokedex.model.*
import com.bushelpowered.pokedex.repository.PokemonRepository
import com.bushelpowered.pokedex.repository.UserRepository
import com.bushelpowered.pokedex.userDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.regex.Pattern


@Service
class UserService(val userRepository: UserRepository, val pokemonRepository: PokemonRepository){

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
        userDetailsService(user.username, user.password)
        userRepository.save(user)

        return ResponseEntity.accepted().body(Message("Hello ${user.username}! Account has been successfully created!"))
    }

    fun checkLogin(login: Login): Any {
        return if (userRepository.existsByEmail(login.email).get()) {

            val validLogin = bCryptPasswordEncoder().matches(login.password, userRepository.findByEmail(login.email).get().password)

            if (validLogin) {
                ResponseEntity.accepted().body(Message("${userRepository.findByEmail(login.email).get().username} is successfully signed in!"))
            } else {
                throw Exception("Invalid email or password, please try again!")
            }
        } else {
            throw Exception("Invalid email or password, please try again!")
        }
    }

    fun modifyCapturedPokemon(userID: Int, response: ModifyCaptured): User {
        if (userRepository.existsById(userID)) {
            val user = userRepository.getReferenceById(userID)

            if (response.remove != null) {
                val removedPokemonObject = pokemonRepository.getReferenceById(response.remove)
                user.capturedPokemon = user.capturedPokemon!! - removedPokemonObject
            }

            if (response.add != null) {
                val addedPokemonObject = pokemonRepository.getReferenceById(response.add)
                user.capturedPokemon = user.capturedPokemon!! + addedPokemonObject
            }

            return userRepository.save(user)
        } else {
            throw Exception("Account does not exist")
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
//                val results: HashMap<String, MutableList<Pokemon>> = hashMapOf()
//                results["capturedPokemon"] = objectList
//                results
                return objectList
            }
        } else {
            throw Exception("Account does not exist")
        }
    }

}
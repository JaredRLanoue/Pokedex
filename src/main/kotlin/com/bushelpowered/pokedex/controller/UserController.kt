package com.bushelpowered.pokedex.controller

import com.bushelpowered.pokedex.model.*
import com.bushelpowered.pokedex.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(val service: UserService){

    @PostMapping("/register")
    fun register(@RequestBody user: User): ResponseEntity<Message> {
        return service.checkValidRegistration(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody login: Login): Any{
        return service.checkLogin(login)
    }

    @PostMapping("/{userID}/modify-captured")
    fun capturePokemon(@PathVariable userID: Int, @RequestBody(required = false) response: ModifyCaptured): User {
        return service.modifyCapturedPokemon(userID, response)
    }

    @GetMapping("/{userID}/captured")
    fun getCapturedPokemon(@PathVariable userID: Int): Any {
        return service.getCapturedPokemon(userID)
    }

}
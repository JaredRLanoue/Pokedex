package com.bushelpowered.pokedex

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.Id
import javax.persistence.Table

@RestController
class Controller {

    @GetMapping("/")
    fun index(): List<Message> = listOf(
        Message("1", "A"),
        Message("2", "B"),
        Message("3", "C")
    )
}

@Table(name = "Messages")
data class Message(@Id val id: String, val letter: String)


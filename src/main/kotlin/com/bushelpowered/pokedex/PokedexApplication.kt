package com.bushelpowered.pokedex

import com.bushelpowered.pokedex.dto.MessageDTO
import com.bushelpowered.pokedex.service.CsvService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@SpringBootApplication
class PokedexApplication {

//    @Bean
//    fun populateDatabase(service: CsvService): ResponseEntity<MessageDTO> {
//        return service.setupDatabase()
//    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

fun main(args: Array<String>) {
    runApplication<PokedexApplication>(*args)
}





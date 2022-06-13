package com.bushelpowered.pokedex

import com.bushelpowered.pokedex.model.Message
import com.bushelpowered.pokedex.service.CsvService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder



@SpringBootApplication
class PokedexApplication {

// Was populating database on startup, but decided to switch to an endpoint because it's faster to test changes
//    @Bean
//    fun populateDatabase(service: CsvService): ResponseEntity<Message>{
//        return service.populateDatabaseWithCsv()
//    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

fun main(args: Array<String>) {
    runApplication<PokedexApplication>(*args)
}





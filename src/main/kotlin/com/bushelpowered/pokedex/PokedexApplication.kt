package com.bushelpowered.pokedex

import com.bushelpowered.pokedex.service.CsvService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.annotation.PostConstruct


@SpringBootApplication
class PokedexApplication

	fun main(args: Array<String>) {
		runApplication<PokedexApplication>(*args)
	}

	@Bean
	fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}





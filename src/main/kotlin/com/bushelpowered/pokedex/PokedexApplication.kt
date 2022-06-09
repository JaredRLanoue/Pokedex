package com.bushelpowered.pokedex

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.stereotype.Component


@SpringBootApplication
class PokedexApplication

	fun main(args: Array<String>) {
		runApplication<PokedexApplication>(*args)
	}

@Bean
fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
	return BCryptPasswordEncoder()
}

@Override
@Bean
fun userDetailsService(username: String, password: String): UserDetailsService {
	val userdetails = User.builder()
		.username(username)
		.password(bCryptPasswordEncoder().encode(password))
		.build()

	return InMemoryUserDetailsManager(userdetails)
}


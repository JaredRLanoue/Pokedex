package com.bushelpowered.pokedex.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Override
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .formLogin().disable()
    }
}
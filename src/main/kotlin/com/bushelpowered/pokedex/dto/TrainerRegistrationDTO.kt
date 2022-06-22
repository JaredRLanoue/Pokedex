package com.bushelpowered.pokedex.dto

data class TrainerRegistrationDTO(
    val username: String,
    val email: String,
    var password: String
) {
    init {
        validate(this) {
            validate(TrainerRegistrationDTO::password).isNotBlank()
        }
    }
}

// Why are the username and password nullable?
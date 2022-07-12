package com.bushelpowered.pokedex.dto.request

import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class TrainerRegistrationRequest(
    val username: String,
    val email: String,
    var password: String
) {
    init {
        validate(this) {
            validate(TrainerRegistrationRequest::username)
                .isNotBlank()
                .isNotNull()
                .isNotEmpty()
            validate(TrainerRegistrationRequest::email)
                .isNotBlank()
                .isNotNull()
                .isNotEmpty()
            validate(TrainerRegistrationRequest::password)
                .isNotBlank()
                .isNotNull()
                .isNotEmpty()
        }
    }
}
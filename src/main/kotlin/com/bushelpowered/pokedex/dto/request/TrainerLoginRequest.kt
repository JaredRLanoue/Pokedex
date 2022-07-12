package com.bushelpowered.pokedex.dto.request

import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class TrainerLoginRequest(
    val email: String,
    val password: String
) {
    init {
        validate(this) {
            validate(TrainerLoginRequest::email)
                .isNotNull()
                .isNotBlank()
                .isNotEmpty()
            validate(TrainerLoginRequest::password)
                .isNotNull()
                .isNotBlank()
                .isNotEmpty()
        }
    }
}

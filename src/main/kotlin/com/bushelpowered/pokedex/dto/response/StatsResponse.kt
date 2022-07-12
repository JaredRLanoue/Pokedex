package com.bushelpowered.pokedex.dto.response

import com.google.gson.annotations.SerializedName

data class StatsResponse(
    val hp: Int,
    val speed: Int,
    val attack: Int,
    val defense: Int,
    @SerializedName("special-attack")
    val specialAttack: Int,
    @SerializedName("special-defense")
    val specialDefense: Int
)

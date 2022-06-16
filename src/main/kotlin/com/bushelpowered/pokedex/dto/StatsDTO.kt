package com.bushelpowered.pokedex.dto

import com.google.gson.annotations.SerializedName

data class StatsDTO(
    val hp: Int,
    val speed: Int,
    val attack: Int,
    val defense: Int,
    @SerializedName("special-attack")
    val specialAttack: Int,
    @SerializedName("special-defense")
    val specialDefense: Int
)

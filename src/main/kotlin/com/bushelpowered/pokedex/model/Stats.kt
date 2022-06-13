package com.bushelpowered.pokedex.model

import com.google.gson.annotations.SerializedName

data class Stats(
    val hp: Int,
    val speed: Int,
    val attack: Int,
    val defense: Int,
    @SerializedName("special-attack")
    val special_attack: Int,
    @SerializedName("special-defense")
    val special_defense: Int
)

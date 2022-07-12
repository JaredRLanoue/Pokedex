package com.bushelpowered.pokedex.util

import com.bushelpowered.pokedex.dto.request.TrainerRegistrationRequest
import com.bushelpowered.pokedex.dto.response.PokemonResponse
import com.bushelpowered.pokedex.dto.response.StatsResponse
import com.bushelpowered.pokedex.dto.response.TrainerCaptureResponse
import com.bushelpowered.pokedex.entity.Pokemon
import com.bushelpowered.pokedex.entity.Trainer

fun Pokemon.toPokemonDto() = PokemonResponse(
    id = id,
    name = name,
    height = height,
    genus = genus,
    weight = weight,
    description = description,
    types = types!!.map { it.type },
    abilities = abilities!!.map { it.ability },
    eggGroups = eggGroups!!.map { it.eggGroup },
    stats = StatsResponse(
        hp = hp,
        speed = speed,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense
    )
)

fun Trainer.toTrainerDto() = TrainerCaptureResponse(
    username = username,
    email = email,
    capturedPokemon = capturedPokemon.map { (it.toPokemonDto()) }
)

fun TrainerRegistrationRequest.toTrainerEntity() = Trainer(
    username = username,
    email = email,
    password = password
)

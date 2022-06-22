package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.dto.PokemonDTO
import com.bushelpowered.pokedex.dto.StatsDTO
import com.bushelpowered.pokedex.dto.TrainerCaptureDTO
import com.bushelpowered.pokedex.dto.TrainerRegistrationDTO
import com.bushelpowered.pokedex.entity.Pokemon
import com.bushelpowered.pokedex.entity.Trainer
import org.springframework.stereotype.Service


fun Pokemon.toDto() = PokemonDTO(
    id = id,
    name = name,
    height = height,
    genus = pokemon.genus,
    weight = pokemon.weight,
    description = pokemon.description,
    types = pokemon.types!!.map { it.type },
    abilities = pokemon.abilities!!.map { it.ability },
    eggGroups = pokemon.eggGroups!!.map { it.eggGroup },
    stats = StatsDTO(
        hp = pokemon.hp,
        speed = pokemon.speed,
        attack = pokemon.attack,
        defense = pokemon.defense,
        specialAttack = pokemon.specialAttack,
        specialDefense = pokemon.specialDefense

    )
)

@Service
class ConverterService {

    // These functions should be extension functions instead of their own service
    fun pokemonEntityToDTO(pokemon: Pokemon): PokemonDTO {
        return PokemonDTO(
            id = pokemon.id,
            name = pokemon.name,
            height = pokemon.height,
            genus = pokemon.genus,
            weight = pokemon.weight,
            description = pokemon.description,
            types = pokemon.types!!.map { it.type },
            abilities = pokemon.abilities!!.map { it.ability },
            eggGroups = pokemon.eggGroups!!.map { it.eggGroup },
            stats = StatsDTO(
                hp = pokemon.hp,
                speed = pokemon.speed,
                attack = pokemon.attack,
                defense = pokemon.defense,
                specialAttack = pokemon.specialAttack,
                specialDefense = pokemon.specialDefense

            )
        )
    }

    fun trainerEntityToDTO(trainer: Trainer): TrainerCaptureDTO {
        return TrainerCaptureDTO(
            username = trainer.username,
            email = trainer.email,
            capturedPokemon = trainer.capturedPokemon!!.map { pokemonEntityToDTO(it) }
        )
    }

    fun trainerDTOToEntity(trainerDTO: TrainerRegistrationDTO): Trainer {
        return Trainer(
            id = null,
            username = trainerDTO.username!!,
            email = trainerDTO.email!!,
            password = trainerDTO.password!!,
            capturedPokemon = null
        )
    }
}
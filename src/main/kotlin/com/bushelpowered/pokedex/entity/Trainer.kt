package com.bushelpowered.pokedex.entity

import javax.persistence.*

@Entity
@Table(name = "trainer")
data class Trainer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val username: String,
    val email: String,
    var password: String,
    @OneToMany
    @JoinTable(
        name = "trainer_captured",
        joinColumns = [JoinColumn(name = "trainer_id")],
        inverseJoinColumns = [JoinColumn(name = "pokemon_id")]
    )
    var capturedPokemon: List<Pokemon> = listOf()
)

package com.bushelpowered.pokedex.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "trainer")
data class Trainer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val username: String,
    val email: String,
    var password: String,
    @OneToMany
    @JoinTable(
        name = "trainer_captured",
        joinColumns = [JoinColumn(name = "trainer_id")],
        inverseJoinColumns = [JoinColumn(name = "pokemon_id")]
    )
    var capturedPokemon: List<Pokemon> = listOf() // This doesn't need to be nullable. You could initialize this to an empty list
)

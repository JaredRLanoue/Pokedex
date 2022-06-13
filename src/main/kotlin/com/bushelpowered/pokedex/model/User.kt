package com.bushelpowered.pokedex.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val username: String,
    val email: String,
    @JsonIgnore
    var password: String,
    @OneToMany
    @JoinTable(
        name = "user_captured",
        joinColumns = [ JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "pokemon_id")])
    var capturedPokemon: List<Pokemon>?
)

package com.bushelpowered.pokedex.model

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreType
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String,
    @OneToMany
    @JoinTable(
        name = "user_captured",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "pokemon_id")]
    )
    var capturedPokemon: List<Pokemon>?
)

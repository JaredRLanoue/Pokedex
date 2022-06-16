package com.bushelpowered.pokedex.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "abilities")
data class Abilities(
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    var ability: String
)

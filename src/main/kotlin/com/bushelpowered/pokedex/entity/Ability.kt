package com.bushelpowered.pokedex.entity

import javax.persistence.*

@Entity
@Table(name = "abilities")
data class Ability(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    var ability: String
)

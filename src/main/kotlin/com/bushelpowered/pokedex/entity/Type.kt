package com.bushelpowered.pokedex.entity

import javax.persistence.*

@Entity
@Table(name = "types")
data class Type(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    var type: String
)

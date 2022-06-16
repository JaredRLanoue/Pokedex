package com.bushelpowered.pokedex.entity

import javax.persistence.*

@Entity
@Table(name = "types")
data class Types(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    var type: String,
)

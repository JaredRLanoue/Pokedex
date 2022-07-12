package com.bushelpowered.pokedex.entity

import javax.persistence.*

@Entity
@Table(name = "egg_groups")
data class EggGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val eggGroup: String
)
package com.bushelpowered.pokedex.entity

import javax.persistence.*

@Entity
@Table(name = "egg_groups")
data class EggGroups(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val eggGroup: String
)
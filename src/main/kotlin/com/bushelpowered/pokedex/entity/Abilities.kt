package com.bushelpowered.pokedex.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "abilities")
data class Abilities(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?, // Why is the id nullable?
    var ability: String
)

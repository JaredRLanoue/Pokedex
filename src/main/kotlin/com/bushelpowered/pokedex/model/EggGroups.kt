package com.bushelpowered.pokedex.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "egg_groups")
data class EggGroups(
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    @Column(name = "eggGroups")
    val eggGroup: String = "N/A"
)
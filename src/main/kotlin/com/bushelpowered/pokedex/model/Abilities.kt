package com.bushelpowered.pokedex.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "abilities")
data class Abilities(
    @JsonIgnore
    @Id
    val id: Int?,
    @Column(name = "abilities")
    val ability: String = "N/A"
)

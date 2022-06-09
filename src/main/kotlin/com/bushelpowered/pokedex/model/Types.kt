package com.bushelpowered.pokedex.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "types")
data class Types(
    @JsonIgnore
    @Id
    val id: Int,
    @Column(name = "types")
    var type: String
)

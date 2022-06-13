package com.bushelpowered.pokedex.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*

@Entity
@Table(name = "types")
data class Types(
    @Id
    @JsonIgnore
    @JoinColumn(name = "types_id", nullable = true)
    val id: Int,
    var typeOne: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var typeTwo: String?
)

package com.bushelpowered.pokedex.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*

@Entity
@Table(name = "abilities")
data class Abilities(
    @Id
    @JsonIgnore
    @JoinColumn(name = "abilities_id")
    val id: Int?,
    var abilityOne: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var abilityTwo: String?,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var abilityThree: String?
)

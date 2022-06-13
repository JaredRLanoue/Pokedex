package com.bushelpowered.pokedex.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*

@Entity
@Table(name = "egg_groups")
data class EggGroups(
    @JsonIgnore
    @Id
    @JoinColumn(name = "egg_groups_id")
    val id: Int?,
    val eggGroupOne: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val eggGroupTwo: String?
)
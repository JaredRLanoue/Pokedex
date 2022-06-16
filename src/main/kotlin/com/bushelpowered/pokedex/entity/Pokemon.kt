package com.bushelpowered.pokedex.entity

import javax.persistence.*

@Entity
@Table(name = "pokemon")
data class Pokemon(
    @Id
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val genus: String,
    val description: String,
    val hp: Int,
    val speed: Int,
    val attack: Int,
    val defense: Int,
    @Column(name = "special_attack")
    val specialAttack: Int,
    @Column(name = "special_defense")
    val specialDefense: Int,
    @OneToMany
    @JoinTable(
        name = "pokemon_types",
        joinColumns = [JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "types_id")]
    )
    val types: List<Types>?,

    @OneToMany
    @JoinTable(
        name = "pokemon_egg_groups",
        joinColumns = [JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "egg_groups_id")]
    )
    val eggGroups: List<EggGroups>?,

    @OneToMany
    @JoinTable(
        name = "pokemon_abilities",
        joinColumns = [JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "abilities_id")]
    )
    val abilities: List<Abilities>?
)

package com.bushelpowered.pokedex.model

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import liquibase.repackaged.com.opencsv.bean.CsvBindByName
import javax.persistence.*

@Entity
@Table(name = "pokemon")
data class Pokemon(
    @Id
    val id: Int,
    val name: String,
    val height: Int?,
    val weight: Int?,
    val genus: String?,
    val description: String?,
    val hp: Int?,
    val speed: Int?,
    val attack: Int?,
    val defense: Int?,
    @Column(name = "special_attack")
    val specialAttack: Int?,
    @Column(name = "special_defense")
    val specialDefense: Int?,

    @ManyToMany
    @JoinTable(
        name = "pokemon_types",
        joinColumns = [ JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "types_id")])
    var types: List<Types>,

    @ManyToMany
    @JoinTable(
        name = "pokemon_egg_groups",
        joinColumns = [ JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "egg_groups_id")])
    val eggGroups: List<EggGroups>,

    @ManyToMany
    @JoinTable(
        name = "pokemon_abilities",
        joinColumns = [ JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "abilities_id")])
    val abilities: Set<Abilities>
)

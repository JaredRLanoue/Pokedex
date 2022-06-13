package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.model.*
import com.bushelpowered.pokedex.repository.AbilitiesRepository
import com.bushelpowered.pokedex.repository.EggGroupRepository
import com.bushelpowered.pokedex.repository.PokemonRepository
import com.bushelpowered.pokedex.repository.TypeRepository
import com.google.gson.Gson
import liquibase.repackaged.com.opencsv.CSVReader
import liquibase.repackaged.com.opencsv.CSVReaderBuilder
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.io.FileReader


@Service
class CsvService(
    val pokemonRepository: PokemonRepository,
    val typeRepository: TypeRepository,
    val abilitiesRepository: AbilitiesRepository,
    val eggGroupRepository: EggGroupRepository
) {

    fun populateDatabaseWithCsv(): ResponseEntity<Message> {
        val fileContents = readCsvFile()

        if (fileContents != null) {
            for (pokemon in fileContents) {
                val pokemonObject = turnDataIntoObjects(pokemon)
                pokemonRepository.save(pokemonObject)
            }
        } else {
            throw Exception("CSV File Is Empty!")
        }
        return ResponseEntity.accepted().body(Message("Finished Importing CSV Into Database!"))
    }

    fun readCsvFile(): MutableList<Array<String>>? {
        val csvReader: CSVReader =
            CSVReaderBuilder(FileReader("src/main/resources/db/pokedex.csv")).withSkipLines(1).build()
        return csvReader.readAll()
    }

    fun convertStringToList(uncleanString: String): List<String> {
        return uncleanString.replace(Regex("[^A-Za-z0-9-,]"), "").split(",").toList()
    }

    fun turnDataIntoObjects(pokemon: Array<String>): Pokemon {
        val statsJson = Gson().fromJson(pokemon[7], Stats::class.java)
        val typesList = convertStringToList(pokemon[2])
        val abilitiesList = convertStringToList(pokemon[5])
        val eggGroupsList = convertStringToList(pokemon[6])

        val typesObject = Types(
            pokemon[0].toInt(), typesList[0], typesList.getOrNull(1)
        )

        val abilitiesObject = Abilities(
            pokemon[0].toInt(), abilitiesList[0], abilitiesList.getOrNull(1), abilitiesList.getOrNull(2)
        )

        val eggGroupsObject = EggGroups(
            pokemon[0].toInt(), eggGroupsList[0], eggGroupsList.getOrNull(1)
        )

        typeRepository.save(typesObject)
        abilitiesRepository.save(abilitiesObject)
        eggGroupRepository.save(eggGroupsObject)

        return Pokemon(
            id = pokemon[0].toInt(),
            name = pokemon[1],
            height = pokemon[3].toInt(),
            weight = pokemon[4].toInt(),
            genus = pokemon[8],
            description = pokemon[9],
            hp = statsJson.hp,
            speed = statsJson.speed,
            attack = statsJson.attack,
            defense = statsJson.defense,
            specialAttack = statsJson.special_attack,
            specialDefense = statsJson.special_defense,
            types = listOf(typesObject),
            eggGroups = listOf(eggGroupsObject),
            abilities = listOf(abilitiesObject)
        )
    }
}
package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.dto.MessageDTO
import com.bushelpowered.pokedex.dto.StatsDTO
import com.bushelpowered.pokedex.entity.Abilities
import com.bushelpowered.pokedex.entity.EggGroups
import com.bushelpowered.pokedex.entity.Pokemon
import com.bushelpowered.pokedex.entity.Types
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
class DatabaseService(
    private val pokemonRepository: PokemonRepository,
    private val typeRepository: TypeRepository,
    private val abilitiesRepository: AbilitiesRepository,
    private val eggGroupRepository: EggGroupRepository
) {

    fun setupDatabase(): ResponseEntity<MessageDTO> {
        val fileContents = readCsvFile()
        if(pokemonRepository.findAll().toList().isEmpty()) {
            val uniqueTypes = findUniqueStrings(fileContents, 2)
            val uniqueAbilities = findUniqueStrings(fileContents, 5)
            val uniqueEggGroups = findUniqueStrings(fileContents, 6)
            for (type in uniqueTypes) {
                typeRepository.save(Types(id = null, type = type))
            }
            for (ability in uniqueAbilities) {
                abilitiesRepository.save(Abilities(id = null, ability = ability))
            }
            for (eggGroup in uniqueEggGroups) {
                eggGroupRepository.save(EggGroups(id = null, eggGroup = eggGroup))
            }

            return turnDataIntoPokemon(fileContents)
        } else {
            return ResponseEntity.badRequest().body(MessageDTO("Database has already been uploaded"))
        }
    }

    fun readCsvFile(): MutableList<Array<String>> {
        val csvReader: CSVReader =
            CSVReaderBuilder(FileReader("src/main/resources/db/pokedex.csv")).withSkipLines(1).build()
        val fileContents = csvReader.readAll()
        csvReader.close()
        return fileContents
    }

    fun convertStringToList(uncleanString: String): List<String> {
        val removeFromString = "[^A-Za-z0-9-,]"
        return uncleanString.replace(Regex(removeFromString), "").split(",").toList()
    }

    private fun findUniqueStrings(fileContents: MutableList<Array<String>>, csvIndex: Int): MutableList<String> {
        val uniqueStringList: MutableList<String> = mutableListOf()

        for (listOfStrings in fileContents) {
            val stringList = convertStringToList(listOfStrings[csvIndex])
            for (string in stringList) {
                if (string !in uniqueStringList)
                    // Use the .add function instead
                    uniqueStringList += string
            }
        }
        return uniqueStringList
    }

    fun getAbilitiesFromDatabase(abilitiesStringList: List<String>): List<Abilities> {
        return abilitiesStringList.map { abilitiesRepository.findByAbility(it) }
    }

    fun getEggGroupsFromDatabase(eggGroupsStringList: List<String>): List<EggGroups> {
        return eggGroupsStringList.map { eggGroupRepository.findByEggGroup(it) }
    }

    fun getTypesFromDatabase(typesStringList: List<String>): List<Types> {
        return typesStringList.map { typeRepository.findByType(it) }
    }

    fun turnDataIntoPokemon(fileContents: MutableList<Array<String>>?): ResponseEntity<MessageDTO> {
        for (pokemon in fileContents!!) {
            val statsJson = Gson().fromJson(pokemon[7], StatsDTO::class.java)
            val listOfTypes = getTypesFromDatabase(convertStringToList(pokemon[2]))
            val listOfAbilities = getAbilitiesFromDatabase(convertStringToList(pokemon[5]))
            val listOfEggGroups = getEggGroupsFromDatabase(convertStringToList(pokemon[6]))

            pokemonRepository.save(
                Pokemon(
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
                    specialAttack = statsJson.specialAttack,
                    specialDefense = statsJson.specialDefense,
                    types = listOfTypes,
                    eggGroups = listOfEggGroups,
                    abilities = listOfAbilities
                )
            )
        }
        return ResponseEntity.accepted().body(MessageDTO("Finished Importing CSV Into Database"))
    }


}
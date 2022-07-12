package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.dto.response.MessageResponse
import com.bushelpowered.pokedex.dto.response.StatsResponse
import com.bushelpowered.pokedex.entity.Ability
import com.bushelpowered.pokedex.entity.EggGroup
import com.bushelpowered.pokedex.entity.Pokemon
import com.bushelpowered.pokedex.entity.Type
import com.bushelpowered.pokedex.repository.AbilitiesRepository
import com.bushelpowered.pokedex.repository.EggGroupRepository
import com.bushelpowered.pokedex.repository.PokemonRepository
import com.bushelpowered.pokedex.repository.TypeRepository
import com.google.gson.Gson
import liquibase.repackaged.com.opencsv.CSVReaderBuilder
import org.springframework.http.HttpStatus
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

    fun setupDatabase(): ResponseEntity<MessageResponse> {
        val fileContents = readCsvFile()
        if (pokemonRepository.findAll().toList().isEmpty()) {
            val uniqueTypes = findUniqueStrings(fileContents, 2)
            val uniqueAbilities = findUniqueStrings(fileContents, 5)
            val uniqueEggGroups = findUniqueStrings(fileContents, 6)
            for (type in uniqueTypes) {
                typeRepository.save(Type(type = type))
            }
            for (ability in uniqueAbilities) {
                abilitiesRepository.save(Ability(ability = ability))
            }
            for (eggGroup in uniqueEggGroups) {
                eggGroupRepository.save(EggGroup(eggGroup = eggGroup))
            }
            return turnDataIntoPokemon(fileContents)
        } else {
            return ResponseEntity(MessageResponse("Database has already been uploaded"), HttpStatus.BAD_REQUEST)
        }
    }

    fun readCsvFile(): MutableList<Array<String>> {
        val csvReader = CSVReaderBuilder(FileReader("src/main/resources/db/pokedex.csv"))
            .withSkipLines(1)
            .build()
        val fileContents = csvReader.readAll()
        csvReader.close()
        return fileContents
    }

    fun convertStringToList(uncleanString: String): List<String> {
        val regexPattern = "[^A-Za-z0-9-,]"
        return uncleanString.replace(Regex(regexPattern), "").split(",").toList()
    }

    fun findUniqueStrings(fileContents: MutableList<Array<String>>, csvIndex: Int): MutableList<String> {
        val uniqueStringList: MutableList<String> = mutableListOf()

        for (listOfStrings in fileContents) {
            val stringList = convertStringToList(listOfStrings[csvIndex])
            for (string in stringList) {
                if (string !in uniqueStringList)
                    uniqueStringList.add(string)
            }
        }
        return uniqueStringList
    }

    fun getAbilitiesFromDatabase(abilitiesStringList: List<String>): List<Ability> {
        return abilitiesStringList.map { abilitiesRepository.findByAbility(it) }
    }

    fun getEggGroupsFromDatabase(eggGroupsStringList: List<String>): List<EggGroup> {
        return eggGroupsStringList.map { eggGroupRepository.findByEggGroup(it) }
    }

    fun getTypesFromDatabase(typesStringList: List<String>): List<Type> {
        return typesStringList.map { typeRepository.findByType(it) }
    }

    fun turnDataIntoPokemon(fileContents: MutableList<Array<String>>?): ResponseEntity<MessageResponse> {
        for (pokemon in fileContents!!) {
            val statsJson = Gson().fromJson(pokemon[7], StatsResponse::class.java)
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
        return ResponseEntity(MessageResponse("Finished Importing CSV Into Database"), HttpStatus.OK)
    }

}

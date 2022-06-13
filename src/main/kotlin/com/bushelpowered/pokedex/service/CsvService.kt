package com.bushelpowered.pokedex.service

import com.bushelpowered.pokedex.model.Message
import com.bushelpowered.pokedex.model.Pokemon
import com.bushelpowered.pokedex.model.Stats
import com.bushelpowered.pokedex.model.Types
import com.bushelpowered.pokedex.repository.PokemonRepository
import com.google.gson.Gson
import liquibase.repackaged.com.opencsv.CSVReader
import liquibase.repackaged.com.opencsv.CSVReaderBuilder
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.io.FileReader


@Service
class CsvService(val pokemonRepository: PokemonRepository) {

    fun populateDatabaseWithCsv(): ResponseEntity<Message> {
        val fileContents = readCsvFile()
        if (fileContents != null) {
            for (pokemon in fileContents) {
                val pokemonObject = parseDataIntoObject(pokemon)
                pokemonRepository.save(pokemonObject)
            }
        } else {
            throw Exception("CSV File Is Null!")
        }
        return ResponseEntity.accepted().body(Message("Finished Importing CSV Into Database!"))
    }

    fun readCsvFile(): MutableList<Array<String>>? {
        val csvReader: CSVReader =
            CSVReaderBuilder(FileReader("src/main/resources/db/pokedex.csv")).withSkipLines(1).build()
        return csvReader.readAll()
    }

    fun cleanString(uncleanString: String){
        uncleanString.replace(Regex("[^A-Za-z0-9,]"), "").split(",").toList()
    }

    fun parseDataIntoObject(pokemon: Array<String>): Pokemon {
        val stats = Gson().fromJson(pokemon[7], Stats::class.java)

        val types = cleanString(pokemon[2])
        val abilities = cleanString(pokemon[5])
        val eggGroups = cleanString(pokemon[6])

        return Pokemon(
            pokemon[0].toInt(),
            pokemon[1],
            pokemon[3].toInt(),
            pokemon[4].toInt(),
            pokemon[8],
            pokemon[9],
            stats.hp,
            stats.speed,
            stats.attack,
            stats.defense,
            stats.special_attack,
            stats.special_defense,
            null,
            null,
            null
        )
    }
}
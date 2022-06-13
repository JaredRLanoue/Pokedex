package com.bushelpowered.pokedex.controller

import com.bushelpowered.pokedex.model.Message
import com.bushelpowered.pokedex.service.CsvService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CsvController(val service: CsvService) {

    @PostMapping("/upload")
    fun uploadFile(): ResponseEntity<Message> {
        return service.populateDatabaseWithCsv()
    }

}
package com.bushelpowered.pokedex

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, String>{
    @Query("select * from messages")
    fun findMessages(): List<Message>
}
package com.elliott.dicethrone.domain.game

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface GameRepository : CrudRepository<Game, UUID> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE game CASCADE", nativeQuery = true)
    fun truncateTable()
}
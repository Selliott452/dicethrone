package com.elliott.dicethrone.domain.player

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface PlayerRepository : CrudRepository<Player, UUID> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE player CASCADE", nativeQuery = true)
    fun truncateTable()

}
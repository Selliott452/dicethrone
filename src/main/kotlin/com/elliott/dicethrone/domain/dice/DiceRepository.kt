package com.elliott.dicethrone.domain.dice

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository


interface DiceRepository : CrudRepository<Dice, Int> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE dice CASCADE", nativeQuery = true)
    fun truncateTable()
}
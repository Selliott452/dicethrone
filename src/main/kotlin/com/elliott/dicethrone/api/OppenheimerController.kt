package com.elliott.dicethrone.api

import com.elliott.dicethrone.domain.dice.DiceRepository
import com.elliott.dicethrone.domain.game.GameRepository
import com.elliott.dicethrone.domain.player.PlayerRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class OppenheimerController(
        private val playerRepository: PlayerRepository,
        private val gameRepository: GameRepository,
        private val diceRepository: DiceRepository
) {

    @DeleteMapping("/deleteAll")
    fun deleteAll() {
        gameRepository.deleteAll()
        playerRepository.deleteAll()
        diceRepository.deleteAll()
    }
}
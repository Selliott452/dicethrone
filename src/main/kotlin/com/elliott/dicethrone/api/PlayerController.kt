package com.elliott.dicethrone.api

import com.elliott.dicethrone.domain.player.Player
import com.elliott.dicethrone.domain.player.PlayerRepository
import com.elliott.dicethrone.service.DiceService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/v1/players")
class PlayerController(
        val playerRepository: PlayerRepository,
        val diceService: DiceService
) {

    @DeleteMapping("/delete/{playerId}")
    fun deletePlayer(
            @PathVariable
            playerId: UUID
    ) = if (playerRepository.existsById(playerId)) {
        playerRepository.deleteById(playerId)
    } else {
        throw ResponseStatusException(
                HttpStatus.NOT_FOUND, "Player Not Found"
        )
    }

    @GetMapping("/{playerId}")
    fun getPlayer(
            @PathVariable
            playerId: UUID
    ): Player = playerRepository.findById(playerId).getOrNull()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")

    @PutMapping("/{playerId}/dice/roll")
    fun rollDice(
            @PathVariable
            playerId: UUID,
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.let { player ->
                player.dice.forEach {
                    if (!it.locked) {
                        diceService.rollDice(player.characterId!!, it)
                    }
                }
                player
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{playerId}/dice/roll/{diceId}")
    fun rollDiceById(
            @PathVariable
            playerId: UUID,
            @PathVariable
            diceId: Int
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.let { player ->
                player.dice.find { it.id == diceId }?.apply {
                    if (!this.locked) {
                        diceService.rollDice(player.characterId!!, this)
                    }
                } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Dice Not Found")
                player
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{playerId}/dice/set/{diceId}/{value}")
    fun rollDiceById(
            @PathVariable
            playerId: UUID,
            @PathVariable
            diceId: Int,
            @PathVariable
            value: Int
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.let { player ->
                player.dice.find { it.id == diceId }?.apply {
                    diceService.setDiceValue(value, player.characterId!!, this)
                } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Dice Not Found")
                player
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{playerId}/dice/lock/{diceId}")
    fun lockDice(
            @PathVariable
            playerId: UUID,
            @PathVariable
            diceId: Int
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.apply {
                this.dice.find { it.id == diceId }?.apply {
                    this.locked = true
                } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Dice Not Found")
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{playerId}/dice/unlock/{diceId}")
    fun unlockDice(
            @PathVariable
            playerId: UUID,
            @PathVariable
            diceId: Int
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.apply {
                this.dice.find { it.id == diceId }?.apply {
                    this.locked = false
                } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Dice Not Found")
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{playerId}/dice/unlock/all")
    fun unlockAllDice(
            @PathVariable
            playerId: UUID,
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.apply {
                this.dice.forEach {
                    it.locked = false
                }
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @GetMapping("/{playerId}/health")
    fun getHealth(
            @PathVariable
            playerId: UUID,
    ): Int = playerRepository.findById(playerId).getOrNull()?.health
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")

    @PutMapping("/{playerId}/health/adjust/{delta}")
    fun adjustHealth(
            @PathVariable
            playerId: UUID,
            @PathVariable
            delta: Int
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.apply {
                this.health += delta
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{playerId}/health/set/{value}")
    fun setHealth(
            @PathVariable
            playerId: UUID,
            @PathVariable
            value: Int
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.apply {
                this.health = value
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @GetMapping("/{playerId}/cp")
    fun getCombatPoints(
            @PathVariable
            playerId: UUID,
    ): Int = playerRepository.findById(playerId).getOrNull()?.combatPoints
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")

    @PutMapping("/{playerId}/cp/adjust/{delta}")
    fun adjustCombatPoints(
            @PathVariable
            playerId: UUID,
            @PathVariable
            delta: Int
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.apply {
                this.combatPoints += delta
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{playerId}/cp/set/{value}")
    fun setCombatPoints(
            @PathVariable
            playerId: UUID,
            @PathVariable
            value: Int
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.apply {
                this.combatPoints = value
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )
}


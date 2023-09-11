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
    @PostMapping("/create")
    fun createPlayer(
            @RequestBody createResource: CreatePlayerResource
    ): Player = playerRepository.save(
            Player().apply {
                this.name = createResource.name
                this.characterId = createResource.character
                this.dice.addAll(diceService.getDice())
            }
    )

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

    @GetMapping("/{id}")
    fun getPlayer(
            @PathVariable
            playerId: UUID
    ): Player = playerRepository.findById(playerId).getOrNull()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")

    @PutMapping("/{playerId}/dice/lock")
    fun lockDice(
            @PathVariable
            playerId: UUID,
            @RequestBody
            resource: PlayerDiceResource
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.apply {
                this.dice.find { it.id == resource.id }.apply {
                    this?.locked = true
                }
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{uuid}/dice/unlock")
    fun unlockDice(
            @PathVariable
            playerId: UUID,
            @RequestBody
            resource: PlayerDiceResource
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.apply {
                this.dice.find { it.id == resource.id }.apply {
                    this?.locked = false
                }
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{uuid}/dice/unlock/all")
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

    @GetMapping("/{id}/health")
    fun getHealth(
            @PathVariable
            playerId: UUID,
    ): Int = playerRepository.findById(playerId).getOrNull()?.health
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")

    @PutMapping("/{id}/health/adjust/{delta}")
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

    @PutMapping("/{id}/health/set/{value}")
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
    fun getComboPoints(
            @PathVariable
            playerId: UUID,
    ): Int = playerRepository.findById(playerId).getOrNull()?.comboPoints
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")

    @PutMapping("/{playerId}/cp/adjust/{delta}")
    fun adjustComboPoints(
            @PathVariable
            playerId: UUID,
            @PathVariable
            delta: Int
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.apply {
                this.comboPoints += delta
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{playerId}/cp/set/{value}")
    fun setComboPoints(
            @PathVariable
            playerId: UUID,
            @PathVariable
            value: Int
    ): Player = playerRepository.save(
            playerRepository.findById(playerId).getOrNull()?.apply {
                this.comboPoints = value
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )
}


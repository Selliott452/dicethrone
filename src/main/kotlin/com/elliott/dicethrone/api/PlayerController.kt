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

    @DeleteMapping("/delete/{uuid}")
    fun deletePlayer(
            @PathVariable
            uuid: UUID
    ) = if (playerRepository.existsById(uuid)) {
        playerRepository.deleteById(uuid)
    } else {
        throw ResponseStatusException(
                HttpStatus.NOT_FOUND, "Player Not Found"
        )
    }

    @GetMapping("/{uuid}")
    fun getPlayer(
            @PathVariable
            uuid: UUID
    ): Player = playerRepository.findById(uuid).getOrNull()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")

    @PutMapping("/{uuid}/dice/lock")
    fun lockDice(
            @PathVariable
            uuid: UUID,
            @RequestBody
            resource: PlayerDiceResource
    ): Player = playerRepository.save(
            playerRepository.findById(uuid).getOrNull()?.apply {
                this.dice.find { it.id == resource.id }.apply {
                    this?.locked = true
                }
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{uuid}/dice/unlock")
    fun unlockDice(
            @PathVariable
            uuid: UUID,
            @RequestBody
            resource: PlayerDiceResource
    ): Player = playerRepository.save(
            playerRepository.findById(uuid).getOrNull()?.apply {
                this.dice.find { it.id == resource.id }.apply {
                    this?.locked = false
                }
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{uuid}/dice/unlock/all")
    fun unlockAllDice(
            @PathVariable
            uuid: UUID,
    ): Player = playerRepository.save(
            playerRepository.findById(uuid).getOrNull()?.apply {
                this.dice.forEach {
                    it.locked = false
                }
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @GetMapping("/{uuid}/health")
    fun getHealth(
            @PathVariable
            uuid: UUID,
    ): Int = playerRepository.findById(uuid).getOrNull()?.health
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")

    @PutMapping("/{uuid}/health/adjust/{delta}")
    fun adjustHealth(
            @PathVariable
            uuid: UUID,
            @PathVariable
            delta: Int
    ): Player = playerRepository.save(
            playerRepository.findById(uuid).getOrNull()?.apply {
                this.health += delta
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{uuid}/health/set/{value}")
    fun setHealth(
            @PathVariable
            uuid: UUID,
            @PathVariable
            value: Int
    ): Player = playerRepository.save(
            playerRepository.findById(uuid).getOrNull()?.apply {
                this.health = health
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @GetMapping("/{uuid}/cp")
    fun getComboPoints(
            @PathVariable
            uuid: UUID,
    ): Int = playerRepository.findById(uuid).getOrNull()?.comboPoints
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")

    @PutMapping("/{uuid}/cp/adjust/{delta}")
    fun adjustComboPoints(
            @PathVariable
            uuid: UUID,
            @PathVariable
            delta: Int
    ): Player = playerRepository.save(
            playerRepository.findById(uuid).getOrNull()?.apply {
                this.comboPoints += delta
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )

    @PutMapping("/{uuid}/cp/set/{value}")
    fun setComboPoints(
            @PathVariable
            uuid: UUID,
            @PathVariable
            value: Int
    ): Player = playerRepository.save(
            playerRepository.findById(uuid).getOrNull()?.apply {
                this.comboPoints = value
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
    )
}


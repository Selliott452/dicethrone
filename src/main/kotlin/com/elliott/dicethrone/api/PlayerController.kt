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

    @DeleteMapping("/{uuid}")
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
}


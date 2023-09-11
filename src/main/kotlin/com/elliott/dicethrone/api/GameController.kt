package com.elliott.dicethrone.api

import com.elliott.dicethrone.domain.game.Game
import com.elliott.dicethrone.domain.game.GameRepository
import com.elliott.dicethrone.domain.player.PlayerRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/v1/games")
class GameController(
        val gameRepository: GameRepository,
        val playerRepository: PlayerRepository
) {

    @GetMapping("")
    fun getGames() = gameRepository.findAll()

    @GetMapping("/{uuid}")
    fun getGame(@PathVariable uuid: UUID): Game? =
            gameRepository.findById(uuid).getOrNull()
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not Found")

    @PostMapping("/create")
    fun createGame(@RequestBody name: String): Game =
            gameRepository.save(
                    Game().apply {
                        this.name = name
                    })

    @GetMapping("/{uuid}/players")
    fun getPlayers(@PathVariable uuid: UUID) =
            gameRepository.findById(uuid).getOrNull()?.players
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not Found")

    @PutMapping("/{uuid}/players/add/{playeruuid}")
    fun addPlayer(@PathVariable uuid: UUID,
                  @PathVariable playeruuid: UUID) =
            gameRepository.findById(uuid).getOrNull()?.let {
                it.players.add(
                        playerRepository.findById(playeruuid).getOrNull()
                                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
                )
                gameRepository.save(it)
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not Found")


    @PutMapping("/{uuid}/players/remove/{playeruuid}")
    fun removePlayer(@PathVariable uuid: UUID,
                     @PathVariable playeruuid: UUID) =
            gameRepository.findById(uuid).getOrNull()?.let {
                it.players.remove(
                        playerRepository.findById(playeruuid).getOrNull()
                                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
                )
                gameRepository.save(it)
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not Found")

    @DeleteMapping("/delete/{uuid}")
    fun delete(@PathVariable uuid: UUID) =
            gameRepository.deleteById(uuid)
}
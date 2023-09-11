package com.elliott.dicethrone.api

import com.elliott.dicethrone.domain.game.Game
import com.elliott.dicethrone.domain.game.GameRepository
import com.elliott.dicethrone.domain.player.Player
import com.elliott.dicethrone.domain.player.PlayerRepository
import com.elliott.dicethrone.service.DiceService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/v1/games")
class GameController(
        val gameRepository: GameRepository,
        val playerRepository: PlayerRepository,
        val diceService: DiceService
) {

    @GetMapping("")
    fun getGames() = gameRepository.findAll()

    @GetMapping("/{gameId}")
    fun getGame(@PathVariable gameId: UUID): Game? =
            gameRepository.findById(gameId).getOrNull()
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not Found")

    @PostMapping("/create")
    fun createGame(@RequestBody name: String): Game =
            gameRepository.save(
                    Game().apply {
                        this.name = name
                    })

    @GetMapping("/{gameId}/players")
    fun getPlayers(@PathVariable gameId: UUID) =
            gameRepository.findById(gameId).getOrNull()?.players
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not Found")

    @PutMapping("/{gameId}/players/add/{playerId}")
    fun addPlayer(@PathVariable gameId: UUID,
                  @PathVariable playerId: UUID) =
            gameRepository.findById(gameId).getOrNull()?.let {
                it.players.add(
                        playerRepository.findById(playerId).getOrNull()
                                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
                )
                gameRepository.save(it)
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not Found")


    @PutMapping("/{gameId}/players/remove/{playerId}")
    fun removePlayer(@PathVariable gameId: UUID,
                     @PathVariable playerId: UUID) =
            gameRepository.findById(gameId).getOrNull()?.let {
                it.players.remove(
                        playerRepository.findById(playerId).getOrNull()
                                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found")
                )
                gameRepository.save(it)
            } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not Found")

    @PutMapping("/{gameId}/join")
    fun createPlayer(
            @PathVariable gameId: UUID,
            @RequestBody createResource: CreatePlayerResource
    ): Game = gameRepository.findById(gameId).getOrNull()?.apply {
        this.players.add(
                playerRepository.save(
                        Player().apply {
                            this.userId = createResource.userId
                            this.characterId = createResource.character
                            this.dice.addAll(diceService.getDice())
                        }
                )
        )
    } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not Found")

    @PutMapping("/{gameId}/leave/{playerId}")
    fun createPlayer(
            @PathVariable gameId: UUID,
            @PathVariable playerId: UUID
    ): Game = gameRepository.findById(gameId).getOrNull()?.apply {
        this.players.remove(playerRepository.findById(playerId).getOrNull()
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player Not Found"))
    } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not Found")

    @DeleteMapping("/delete/{gameId}")
    fun delete(@PathVariable gameId: UUID) =
            gameRepository.deleteById(gameId)
}
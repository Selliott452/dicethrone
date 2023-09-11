package com.elliott.dicethrone.api

import com.elliott.dicethrone.domain.game.Game
import com.elliott.dicethrone.domain.game.GameRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/v1/games")
class GameController(
        val repository: GameRepository
) {

    @GetMapping("/{uuid}")
    fun getGame(@PathVariable uuid: UUID): Game? =
            repository.findById(uuid).getOrNull()
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not Found")

    @PostMapping("/create")
    fun createGame(@RequestBody name: String): Game =
            repository.save(
                    Game().apply {
                        this.name = name
                    })

    @DeleteMapping("/delete")
    fun delete(@PathVariable uuid: UUID) =
            repository.deleteById(uuid)
}
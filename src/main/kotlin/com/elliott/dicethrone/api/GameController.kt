package com.elliott.dicethrone.api

import com.elliott.dicethrone.domain.game.Game
import com.elliott.dicethrone.domain.game.GameRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/game")
class GameController(
        val repository: GameRepository
) {

    @GetMapping("/{uuid}")
    fun getGame(@PathVariable uuid: UUID): Game? =
            repository.findById(uuid).get()

    @PostMapping("/create")
    fun createGame(@RequestBody name: String): Game =
            repository.save(
                    Game().apply {
                        this.name = name
                    })
}
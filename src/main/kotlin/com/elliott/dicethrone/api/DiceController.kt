package com.elliott.dicethrone.api

import com.elliott.dicethrone.service.DiceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/dice")
class DiceController(
        val diceService: DiceService
) {

    @GetMapping("/roll/{numberOfDice}")
    fun rollDice(@PathVariable numberOfDice: Int) : List<Int> =
            (1..numberOfDice).map {
                diceService.getRandomValue()
            }


}
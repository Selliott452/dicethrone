package com.elliott.dicethrone.api

import com.elliott.dicethrone.domain.character.Character
import com.elliott.dicethrone.service.CharacterService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/character")
class CharacterController(
        val characterService: CharacterService
) {

    @GetMapping("/{id}")
    fun getStatusEffect(@PathVariable id: String): Character? =
            characterService.characters[id]

    @GetMapping("")
    fun getStatusEffects(): List<Character> =
            characterService.characters.values.toList()

}
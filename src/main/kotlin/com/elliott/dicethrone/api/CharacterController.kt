package com.elliott.dicethrone.api

import com.elliott.dicethrone.domain.character.Character
import com.elliott.dicethrone.domain.character.CharacterId
import com.elliott.dicethrone.service.CharacterService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/v1/characters")
class CharacterController(
        val characterService: CharacterService
) {

    @GetMapping("/{id}")
    fun getCharacter(@PathVariable id: CharacterId): Character =
            characterService.characters[id]
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Character Not Found")

    @GetMapping("")
    fun getCharacters(): List<Character> =
            characterService.characters.values.toList()

}
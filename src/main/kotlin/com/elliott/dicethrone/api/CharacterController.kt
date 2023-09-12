package com.elliott.dicethrone.api

import com.elliott.dicethrone.domain.card.Card
import com.elliott.dicethrone.domain.character.Character
import com.elliott.dicethrone.domain.character.CharacterId
import com.elliott.dicethrone.service.CardService
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
        val characterService: CharacterService,
        val cardService: CardService
) {

    @GetMapping("/{characterId}")
    fun getCharacter(@PathVariable characterId: CharacterId): Character =
            characterService.characters[characterId]
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Character Not Found")

    @GetMapping("")
    fun getCharacters(): List<Character> =
            characterService.characters.values.toList()

    @GetMapping("/available")
    fun getAvailableCharacters(): List<CharacterId> =
            CharacterId.values().toList()

    @GetMapping("/{characterId}/cards")
    fun getCharacterCards(@PathVariable characterId: CharacterId): List<Card> =
            (characterService.characters[characterId]
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Character Not Found"))
                    .cards.map {
                        cardService.cards[it]
                                ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Card Not Found")
                    }

}
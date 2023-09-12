package com.elliott.dicethrone.service

import com.elliott.dicethrone.domain.character.Character
import com.elliott.dicethrone.domain.character.CharacterId
import com.elliott.dicethrone.domain.player.Player
import com.google.gson.Gson
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class CharacterService {

    val characters: Map<CharacterId, Character> = readInCharacters().associateBy { it.id }
    private fun readInCharacters() =
            Gson().fromJson(
                    ClassPathResource("characters/character.json").getContentAsString(Charsets.UTF_8),
                    Array<Character>::class.java
            ).toList()

    fun populateDeck(player: Player) = player.apply {
        deck.addAll(characters[this.characterId]!!.cards.toMutableList())
    }
}
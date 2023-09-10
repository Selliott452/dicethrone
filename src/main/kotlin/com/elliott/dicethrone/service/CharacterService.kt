package com.elliott.dicethrone.service

import com.elliott.dicethrone.domain.character.Character
import com.google.gson.Gson
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class CharacterService {

    val characters: Map<String, Character> = readInCharacters().associateBy { it.id }
    private fun readInCharacters() =
            Gson().fromJson(
                    ClassPathResource("characters/character.json").getContentAsString(Charsets.UTF_8),
                    Array<Character>::class.java
            ).toList()
}
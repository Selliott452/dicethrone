package com.elliott.dicethrone.service

import com.elliott.dicethrone.domain.card.Card
import com.google.gson.Gson
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class CardService {

    val jsonPaths = listOf(
            "cards/DTCardsBarbarian.json",
            "cards/DTCardsHero.json",
            "cards/DTCardsMonk.json",
            "cards/DTCardsMoonElf.json",
            "cards/DTCardsNinja.json",
            "cards/DTCardsPaladin.json",
            "cards/DTCardsTreant.json",
            "cards/DTCardsPyromancer.json",
            "cards/DTCardsShadowThief.json"
    )

    val cards: Map<String, Card> = readInCards().associateBy { it.identifier }
    private fun readInCards() =
            jsonPaths.map {
                readInCardsFile(it)
            }.flatten()

    private fun readInCardsFile(file: String) =
            Gson().fromJson(
                    ClassPathResource(file).getContentAsString(Charsets.UTF_8),
                    Array<Card>::class.java
            ).toList()

}
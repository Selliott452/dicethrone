package com.elliott.dicethrone.api

import com.elliott.dicethrone.domain.card.Card
import com.elliott.dicethrone.service.CardService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/v1/cards")
class CardController(
        val cardService: CardService
) {

    @GetMapping("/{cardId}")
    fun getCard(@PathVariable cardId: String): Card =
            cardService.cards[cardId]
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Card Not Found")

    @GetMapping("")
    fun getCards(): List<Card> =
            cardService.cards.values.toList()

}
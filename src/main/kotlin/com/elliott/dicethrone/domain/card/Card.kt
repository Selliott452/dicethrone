package com.elliott.dicethrone.domain.card

data class Card(
        val identifier: String,
        val name: String,
        val cost: Int,
        val type: CardType,
        val text: String?,
        val effects: List<String> = listOf()
)
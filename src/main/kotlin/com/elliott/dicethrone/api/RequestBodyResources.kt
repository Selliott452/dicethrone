package com.elliott.dicethrone.api


data class CreatePlayerResource(
        val name: String,
        val character: String
)


data class PlayerDiceResource(
        val id: Int
)
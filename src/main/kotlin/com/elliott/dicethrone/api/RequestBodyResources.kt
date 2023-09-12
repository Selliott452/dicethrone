package com.elliott.dicethrone.api

import com.elliott.dicethrone.domain.character.CharacterId


data class CreatePlayerResource(
        val userId: String,
        val character: CharacterId = CharacterId.BARBARIAN
)
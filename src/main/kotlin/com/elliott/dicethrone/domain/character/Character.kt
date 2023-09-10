package com.elliott.dicethrone.domain.character

import com.elliott.dicethrone.domain.ability.Ability
import com.elliott.dicethrone.domain.dice.DiceIcon

class Character(
        val id: String,
        val name: String,
        val dice: List<DiceIcon>,
        val abilities: List<Ability>,
        val cards: List<String>
) {
}
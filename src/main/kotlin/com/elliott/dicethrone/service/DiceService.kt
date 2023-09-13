package com.elliott.dicethrone.service

import com.elliott.dicethrone.domain.character.CharacterId
import com.elliott.dicethrone.domain.dice.Dice
import com.elliott.dicethrone.domain.dice.DiceIcon
import com.elliott.dicethrone.domain.dice.DiceRepository
import org.springframework.stereotype.Service

@Service
class DiceService(
        val characterService: CharacterService,
        val diceRepository: DiceRepository
) {

    fun getDice(): MutableIterable<Dice> =
            diceRepository.saveAll(
                    (1..5).map {
                        Dice().apply {
                            this.diceValue = -1
                            this.locked = false
                            this.icon = DiceIcon.UNDEFINED
                        }
                    }
            )

    fun getRandomValue() = (1..6).random()

    fun setDiceValue(value: Int, characterId: CharacterId, dice: Dice) =
            dice.apply {
                this.diceValue = value
                this.icon = characterService.characters[characterId]?.dice?.get(diceValue - 1) ?: DiceIcon.UNDEFINED
            }

    fun rollDice(character: CharacterId, dice: Dice) =
            setDiceValue(getRandomValue(), character, dice)
}
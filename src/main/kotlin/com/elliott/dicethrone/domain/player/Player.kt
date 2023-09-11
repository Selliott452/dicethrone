package com.elliott.dicethrone.domain.player

import com.elliott.dicethrone.domain.card.Card
import com.elliott.dicethrone.domain.dice.Dice
import jakarta.persistence.*
import java.util.*

@Entity
class Player {

    @Id
    @GeneratedValue
    val uuid: UUID? = null

    @Column
    var name: String = ""

    @Column
    var characterId: String = ""

    @Column
    var health: Int = 30

    @Column
    var combatPoints: Int = 2

    @Transient
    val hand: MutableList<Card> = mutableListOf()

    @Transient
    val deck: MutableList<Card> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinTable(
            name = "player_dice",
            joinColumns = [JoinColumn(name = "player_id", referencedColumnName = "uuid")],
            inverseJoinColumns = [JoinColumn(name = "dice_id", referencedColumnName = "id")]
    )
    val dice: MutableList<Dice> = mutableListOf()


}
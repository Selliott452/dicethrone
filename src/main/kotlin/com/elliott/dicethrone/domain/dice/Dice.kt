package com.elliott.dicethrone.domain.dice

import jakarta.persistence.*


@Entity
class Dice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dice_id_seq")
    @SequenceGenerator(name = "dice_id_seq", allocationSize = 1)
    val id: Int? = null

    @Column
    var diceValue: Int = 1

    @Column
    @Enumerated
    var icon: DiceIcon? = null

    @Column
    var locked: Boolean = false
}
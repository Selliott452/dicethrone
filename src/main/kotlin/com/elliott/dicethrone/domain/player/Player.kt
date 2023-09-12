package com.elliott.dicethrone.domain.player

import com.elliott.dicethrone.domain.character.CharacterId
import com.elliott.dicethrone.domain.dice.Dice
import jakarta.persistence.*
import java.util.*

@Entity
class Player {

    @Id
    @GeneratedValue
    val uuid: UUID? = null

    @Column
    var userId: String = ""

    @Column
    @Enumerated
    var characterId: CharacterId? = null

    @Column
    var health: Int = 30

    @Column
    var combatPoints: Int = 2

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String::class)
    @CollectionTable(name = "player_hand", joinColumns = [JoinColumn(name = "player_id")])
    @Column(name = "card_id", nullable = false)
    @OrderColumn(name = "card_order")
    val hand: MutableList<String> = mutableListOf()

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String::class)
    @CollectionTable(name = "player_deck", joinColumns = [JoinColumn(name = "player_id")])
    @Column(name = "card_id", nullable = false)
    @OrderColumn(name = "card_order")
    val deck: MutableList<String> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinTable(
            name = "player_dice",
            joinColumns = [JoinColumn(name = "player_id", referencedColumnName = "uuid")],
            inverseJoinColumns = [JoinColumn(name = "dice_id", referencedColumnName = "id")]
    )
    val dice: MutableList<Dice> = mutableListOf()

}
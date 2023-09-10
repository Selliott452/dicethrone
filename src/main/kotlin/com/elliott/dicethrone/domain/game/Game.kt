package com.elliott.dicethrone.domain.game

import jakarta.persistence.*
import java.util.*

@Entity
class Game {

    @Id
    @GeneratedValue
    val uuid: UUID? = null

    @Column
    var name: String = ""

    @Transient
    var players: List<String> = listOf()

    @Transient
    var activePlayer: String? = null

    @Column
    @Enumerated
    var phase: Phase = Phase.INCOME

    @Transient
    var stack: List<String> = listOf()

}
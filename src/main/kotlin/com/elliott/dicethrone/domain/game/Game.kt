package com.elliott.dicethrone.domain.game

import com.elliott.dicethrone.domain.player.Player
import jakarta.persistence.*
import java.util.*

@Entity
class Game {

    @Id
    @GeneratedValue
    val uuid: UUID? = null

    @Column
    var name: String = ""

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinTable(
            name = "game_player",
            joinColumns = [JoinColumn(name = "game_id", referencedColumnName = "uuid")],
            inverseJoinColumns = [JoinColumn(name = "player_id", referencedColumnName = "uuid")]
    )
    var players: MutableList<Player> = mutableListOf()

    @Transient
    var activePlayer: Player? = null

    @Column
    @Enumerated
    var phase: Phase? = null

    @Transient
    var stack: MutableList<String> = mutableListOf()

}
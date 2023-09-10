package com.elliott.dicethrone.domain.game

import org.springframework.data.repository.CrudRepository
import java.util.*

interface GameRepository : CrudRepository<Game, UUID> {
}
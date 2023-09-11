package com.elliott.dicethrone.domain.player

import org.springframework.data.repository.CrudRepository
import java.util.*

interface PlayerRepository : CrudRepository<Player, UUID>
package com.elliott.dicethrone.domain.dice

import org.springframework.data.repository.CrudRepository

interface DiceRepository : CrudRepository<Dice, Int>
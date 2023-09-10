package com.elliott.dicethrone.domain.statuseffect


data class StatusEffect(
        val identifier: String,
        val type: StatusEffectType,
        val name: String,
        val stackLimit: Int,
        val description: String,
        val shortDescription: String,
        val effects: List<String>
)
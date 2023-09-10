package com.elliott.dicethrone.service

import com.elliott.dicethrone.domain.statuseffect.StatusEffect
import com.google.gson.Gson
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import kotlin.text.Charsets.UTF_8


@Service
class StatusEffectService(
        val resourceLoader: ResourceLoader
) {

    val statusEffects: Map<String, StatusEffect> = readInStatusEffects().associateBy { it.identifier }


    fun getStatusEffect(identifier: String) = statusEffects[identifier]

    private fun readInStatusEffects() =
            Gson().fromJson(
                    ClassPathResource("statuseffects/status.json").getContentAsString(UTF_8),
                    Array<StatusEffect>::class.java
            ).toList()
}

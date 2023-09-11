package com.elliott.dicethrone.service

import com.elliott.dicethrone.domain.statuseffect.StatusEffect
import com.google.gson.Gson
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import kotlin.text.Charsets.UTF_8


@Service
class StatusEffectService {

    val statusEffects: Map<String, StatusEffect> = readInStatusEffects().associateBy { it.identifier }

    private fun readInStatusEffects() =
            listOf("statuseffects/DTStatEffCompanion.json",
                    "statuseffects/DTStatEffNeg.json",
                    "statuseffects/DTStatEffPos.json",
                    "statuseffects/DTStatEffUnique.json"
            ).map {
                Gson().fromJson(
                        ClassPathResource(it).getContentAsString(UTF_8),
                        Array<StatusEffect>::class.java
                ).toList()
            }.flatten()

}

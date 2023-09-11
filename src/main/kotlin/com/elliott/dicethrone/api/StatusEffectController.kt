package com.elliott.dicethrone.api

import com.elliott.dicethrone.domain.statuseffect.StatusEffect
import com.elliott.dicethrone.service.StatusEffectService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/v1/statuseffects")
class StatusEffectController(
        val statusEffectService: StatusEffectService
) {

    @GetMapping("/{id}")
    fun getStatusEffect(@PathVariable id: String): StatusEffect =
            statusEffectService.statusEffects[id]
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Status Effect Not Found")

    @GetMapping("")
    fun getStatusEffects(): List<StatusEffect> =
            statusEffectService.statusEffects.values.toList()

}
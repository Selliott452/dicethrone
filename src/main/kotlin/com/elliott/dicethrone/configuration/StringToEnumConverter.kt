package com.elliott.dicethrone.configuration

import com.elliott.dicethrone.domain.character.CharacterId
import org.springframework.core.convert.converter.Converter
import java.util.*


class StringToEnumConverter : Converter<String, CharacterId> {
    override fun convert(source: String): CharacterId {
        return CharacterId.valueOf(source.uppercase(Locale.getDefault()))
    }
}
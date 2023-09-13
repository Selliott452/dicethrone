package com.elliott.dicethrone.api

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/v1/resources")
class ResourcesController {
    @GetMapping(value = ["/images/{imageName}"], produces = [MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    fun getImage(
            @PathVariable
            imageName: String
    ): ByteArray =
            javaClass.getResourceAsStream("/images/$imageName")?.readBytes()
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Image Not Found")


}
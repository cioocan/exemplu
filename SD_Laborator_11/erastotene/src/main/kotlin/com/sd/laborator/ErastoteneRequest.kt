package com.sd.laborator

import io.micronaut.core.annotation.Introspected

@Introspected
class ErastoteneRequest {
    private lateinit var number: Integer

    fun getNumber():Int{
        return number.toInt()
    }
}
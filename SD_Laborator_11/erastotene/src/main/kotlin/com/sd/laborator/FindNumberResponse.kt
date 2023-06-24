package com.sd.laborator

import io.micronaut.core.annotation.Introspected

@Introspected
class FindNumberResponse {
    private var message: String? = null
    private var numere: List<Pair<Int, Int>>? = null

    fun getNumere(): List<Pair<Int, Int>>?{
        return this.numere
    }

    fun setNumere(numere: List<Pair<Int, Int>>?) {
        this.numere = numere
    }

    fun getMessage(): String? {
        return message
    }
    fun setMessage(message: String?) {
        this.message = message
    }
}
package com.sd.laborator

import jakarta.inject.Singleton
import java.util.ArrayList

@Singleton
class ErastoteneSirNou {
    val MAX_SIZE = 1000001

    fun calculateSequenceTerms(n: Int): List<Int> {
        val sequenceTerms = mutableListOf<Int>()
        var currentTerm = 1

        for (i in 1..n) {
            sequenceTerms.add(currentTerm)
            currentTerm = currentTerm + 2 * (currentTerm / i)
        }

        return sequenceTerms
    }
}
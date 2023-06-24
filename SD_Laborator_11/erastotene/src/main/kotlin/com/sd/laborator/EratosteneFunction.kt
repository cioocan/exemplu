package com.sd.laborator

import io.micronaut.function.FunctionBean
import io.micronaut.function.executor.FunctionInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.Function
import jakarta.inject.Inject

@FunctionBean("eratostene")
class EratosteneFunction: FunctionInitializer(), Function<ErastoteneRequest, ErastoteneResponse> {
    @Inject
    private lateinit var _erastoteneSirNou: ErastoteneSirNou
    private val LOG:Logger = LoggerFactory.getLogger(EratosteneFunction::class.java)

    override fun apply(msg: ErastoteneRequest): ErastoteneResponse {
        val number = msg.getNumber()
        val response = ErastoteneResponse()

        if (number >= _erastoteneSirNou.MAX_SIZE) {
            LOG.error("Parametru prea mare! $number > maximul de ${_erastoteneSirNou.MAX_SIZE}")
            response.setMessage("Se accepta doar parametri mai mici ca " + _erastoteneSirNou.MAX_SIZE)
            return response
        }

        LOG.info("Se calculeaza primele $number numere prime ...")

        response.setPrimes(_erastoteneSirNou.calculateSequenceTerms(number))
        response.setMessage("Calcul efectuat cu succes!")

        LOG.info("Calcul incheiat!")
        return response
    }
}

/*
fun main(args : Array<String>) {
    val function = EratosteneFunction()
    function.run(args) { context -> function.apply(context.get(ErastoteneRequest::class.java)) }
}*/

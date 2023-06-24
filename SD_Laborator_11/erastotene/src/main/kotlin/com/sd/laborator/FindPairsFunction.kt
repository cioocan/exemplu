package com.sd.laborator

import io.micronaut.function.FunctionBean
import io.micronaut.function.executor.FunctionInitializer
import io.micronaut.http.annotation.Controller
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.Supplier

@Controller("find_paris")
class FindPairsFunction: FunctionInitializer(), Supplier<FindNumberResponse> {
    @Inject
    private lateinit var _gasireNumere: GasireNumere
    private val LOG: Logger = LoggerFactory.getLogger(FindPairsFunction::class.java)

    override fun get(): FindNumberResponse {
        val response = FindNumberResponse()

        LOG.info("Se calculeaza primele perechile a b")

        val numere = _gasireNumere.findNumbers()
        if (numere != null){
            response.setNumere(numere)
            response.setMessage("Calcul efectuat cu succes!")
        }else{
            LOG.error("Nu au fost gasite numerel a,b!")
        }
        return response
    }
}

fun main(args : Array<String>) {
    val function = FindPairsFunction()
    function.run(args) { function.get() }
}
package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.integration.annotation.Transformer
import java.io.File
import java.io.FileWriter
import kotlin.math.abs
import kotlin.random.Random
import kotlin.random.nextUInt

@EnableBinding(Processor::class)
@SpringBootApplication
class FacturareMicroservice {
    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    ///TODO - parametrul ar trebui sa fie doar numarul de inregistrare al comenzii si atat
    fun emitereFactura(identificatorComanda: Int?): Int? {
        val comanda = gasireComanda(identificatorComanda)
        println("Emit factura pentru comanda ${comanda.first}, ${comanda.second}...")
        val nrFactura = abs(Random.nextInt())
        println("S-a emis factura cu nr $nrFactura.")

        FileWriter("/home/student/Documents/SD_labs/SD_Laborator_09/exemplul 2/Facturare/facturi", true).use { writer ->
            writer.append("$comanda-$nrFactura\n")
        }

        return identificatorComanda
    }

    private fun gasireComanda(identificatorComanda: Int?): Pair<String, Int> {
        var found: Pair<String, Int>? = null
        File("/home/student/Documents/SD_labs/SD_Laborator_09/exemplul 2/Comanda/comenzi").forEachLine {
            val parts = it.split("-")
            val numarInregistrare = parts[0].trim().toInt()
            val produs = parts[1].trim()
            val cantitate = parts[2].trim().toInt()
            if (numarInregistrare == identificatorComanda) {
                found =  produs to cantitate
            }
        }
        return found!!
    }
}

fun main(args: Array<String>) {
    runApplication<FacturareMicroservice>(*args)
}

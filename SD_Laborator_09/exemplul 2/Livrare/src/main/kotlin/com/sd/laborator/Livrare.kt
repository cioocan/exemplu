package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import java.io.File

@EnableBinding(Sink::class)
@SpringBootApplication
class LivrareMicroservice {
    @StreamListener(Sink.INPUT)
    ///TODO - parametrul ar trebui sa fie doar numarul de inregistrare al comenzii si atat
    fun expediereComanda(identificatorComanda: Int?) {
        val comanda = gasireComanda(identificatorComanda)
        println("S-a expediat urmatoarea comanda: ${comanda.first}, ${comanda.second}")
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
    runApplication<LivrareMicroservice>(*args)
}
package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.integration.annotation.Transformer
import org.springframework.messaging.support.MessageBuilder
import java.io.File
import kotlin.random.Random

@EnableBinding(Processor::class)
@SpringBootApplication
class DepozitMicroservice {
    companion object {
        var stocProduse = File("/home/student/Documents/SD_labs/SD_Laborator_09/exemplul 2/Depozit/stoc").readLines().map {
            it.split("-").let {
                (name, count) -> name.trim() to count.trim().toInt()
            }
        }
    }

    private fun acceptareComanda(identificator: Int): String {
        println("Comanda cu identificatorul $identificator a fost acceptata!")

        val (produs, cantitate) = gasireProdus(identificator)

        return pregatireColet(produs, cantitate)
    }

    private fun respingereComanda(identificator: Int): String {
        println("Comanda cu identificatorul $identificator a fost respinsa! Stoc insuficient.")
        return "RESPINSA"
    }

    private fun verificareStoc(produs: String, cantitate: Int): Boolean {
        val product = stocProduse.find { it.first == produs }
        return product != null && cantitate <= product.second
    }

    private fun pregatireColet(produs: String, cantitate: Int): String {
        ///TODO - retragere produs de pe stoc in cantitatea specificata
        println("Produsul $produs in cantitate de $cantitate buc. este pregatit de livrare.")


        val produsGasit = stocProduse.find { it.first == produs }
        val remainingQuantity = produsGasit!!.second - cantitate

        File("/home/student/Documents/SD_labs/SD_Laborator_09/exemplul 2/Depozit/stoc").printWriter().use { writer ->
            stocProduse.forEach { (name, count) ->
                if(name != produs)
                    writer.println("$name-$count")
                else
                    writer.println("$produs-$remainingQuantity")
            }
        }

        stocProduse = File("/home/student/Documents/SD_labs/SD_Laborator_09/exemplul 2/Depozit/stoc").readLines().map {
            it.split("-").let {
                    (name, count) -> name.trim() to count.trim().toInt()
            }
        }

        return "$produs|$cantitate"
    }

    private fun gasireProdus(identificatorComanda: Int?): Pair<String, Int> {
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

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    ///TODO - parametrul ar trebui sa fie doar numarul de inregistrare al comenzii si atat
    fun procesareComanda(identificatorComanda: Int?): Int {
        println("Procesez comanda cu identificatorul $identificatorComanda...")

        val (produs, cantitate) = gasireProdus(identificatorComanda)

        //TODO - procesare comanda in depozit
        val rezultatProcesareComanda: String = if (verificareStoc(produs, cantitate)) {
            acceptareComanda(identificatorComanda!!)
        } else {
            respingereComanda(identificatorComanda!!)
        }

        ///TODO - in loc sa se trimita mesajul cu toate datele in continuare, trebuie trimis doar ID-ul comenzii
        return identificatorComanda
    }
}

fun main(args: Array<String>) {
    var found: Pair<String, Int>? = null
    File("/home/student/Documents/SD_labs/SD_Laborator_09/exemplul 2/Comanda/comenzi").forEachLine {
        val parts = it.split("-")
        val numarInregistrare = parts[0].trim().toInt()
        val produs = parts[1].trim()
        val cantitate = parts[2].trim().toInt()
        if (numarInregistrare == 176220070) {
            found =  produs to cantitate
        }
    }
    print(found)
}

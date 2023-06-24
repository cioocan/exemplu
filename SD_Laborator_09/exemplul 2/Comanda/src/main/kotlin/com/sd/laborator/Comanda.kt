package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.integration.annotation.Transformer
import java.io.FileWriter
import java.math.BigInteger
import java.security.MessageDigest

@EnableBinding(Processor::class)
@SpringBootApplication
class ComandaMicroservice {

    private fun generareNumarInregistrare(seedString: String, seedInt: Int): Int {
        val combinedString = seedString + seedInt.toString()

        val md5Digest = MessageDigest.getInstance("MD5")
        val hashBytes = md5Digest.digest(combinedString.toByteArray())

        val bigInteger = BigInteger(1, hashBytes)

        return bigInteger.mod(BigInteger.valueOf(Int.MAX_VALUE.toLong())).toInt()
    }

    private fun pregatireComanda(produs: String, cantitate: Int): Int {
        println("Se pregateste comanda $cantitate x \"$produs\"...")

        val numarInregistrare = generareNumarInregistrare(produs, cantitate)

        FileWriter("/home/student/Documents/SD_labs/SD_Laborator_09/exemplul 2/Comanda/comenzi", true).use { writer ->
            writer.append("$numarInregistrare-$produs-$cantitate\n")
        }

        return numarInregistrare
    }

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    fun preluareComanda(comanda: String?): Int {
        val (identitateClient, produsComandat, cantitate, adresaLivrare) = comanda!!.split("|")
        println("Am primit comanda urmatoare: ")
        println("$identitateClient | $produsComandat | $cantitate | $adresaLivrare")

        ///TODO - in loc sa se trimita mesajul cu toate datele in continuare, trebuie trimis doar ID-ul comenzii
        return pregatireComanda(produsComandat, cantitate.toInt())
    }
}

fun main(args: Array<String>) {
    runApplication<ComandaMicroservice>(*args)
}
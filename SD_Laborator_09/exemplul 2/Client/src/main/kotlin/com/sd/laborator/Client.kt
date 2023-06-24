package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.InboundChannelAdapter
import org.springframework.integration.annotation.Poller
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import java.io.File
import kotlin.random.Random

@EnableBinding(Source::class)
@SpringBootApplication
class ClientMicroservice {
    companion object {
        val listaProduse: List<String> = File("/home/student/Documents/SD_labs/SD_Laborator_09/exemplul 2/Client/produse").useLines { it.toList() }
        val clientMap = File("/home/student/Documents/SD_labs/SD_Laborator_09/exemplul 2/Client/client").useLines { lines ->
            lines.associate {
                it.split("-").let {
                    (name, address) -> name to address
                }
            }
        }
    }

    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = [Poller(fixedDelay = "10000", maxMessagesPerPoll = "1")])
    fun comandaProdus(): () -> Message<String> {
        return {
            val produsComandat = listaProduse[(0 until listaProduse.size).shuffled()[0]]
            val cantitate: Int = Random.nextInt(1, 100)
            val client = clientMap.entries.toList().random()
            ///TODO - datele clientului sa fie citite dinamic si inregistrate in baza de date

            val mesaj = "${client.key}|$produsComandat|$cantitate|${client.value}"
            MessageBuilder.withPayload(mesaj).build()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ClientMicroservice>(*args)
}
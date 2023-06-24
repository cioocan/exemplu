package com.sd.laborator.business.services

import com.sd.laborator.business.interfaces.IReadBeerService
import com.sd.laborator.models.Beer
import com.sd.laborator.persistence.interfaces.IBeerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class ReadBeerService : IReadBeerService {
    @Autowired
    private lateinit var _beerRepository: IBeerRepository
    private var _pattern: Pattern = Pattern.compile("\\W")

    override fun getBeers(): String {
        val result: MutableList<Beer?> = _beerRepository.getAll()
        var stringResult: String = ""
        for (item in result) {
            stringResult += item
        }
        return stringResult
    }

    override fun getBeerByName(name: String): String? {
        if(_pattern.matcher(name).find()) {
            println("SQL Injection for beer name")
            return null
        }
        val result = _beerRepository.getByName(name)
        return result.toString()
    }

    override fun getBeerByPrice(price: Float): String {
        val result = _beerRepository.getByPrice(price)
        var stringResult: String = ""
        for (item in result) {
            stringResult += item
        }
        return stringResult
    }
}
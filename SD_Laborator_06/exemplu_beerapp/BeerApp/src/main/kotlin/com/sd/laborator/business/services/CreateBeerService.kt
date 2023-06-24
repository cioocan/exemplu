package com.sd.laborator.business.services

import com.sd.laborator.business.interfaces.ICreateBeerService
import com.sd.laborator.models.Beer
import com.sd.laborator.persistence.interfaces.IBeerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class CreateBeerService : ICreateBeerService {
    @Autowired
    private lateinit var _beerRepository: IBeerRepository
    private var _pattern: Pattern = Pattern.compile("\\W")

    override fun createBeerTable() {
        _beerRepository.createTable()
    }

    override fun addBeer(beer: Beer) {
        if(_pattern.matcher(beer.beerName).find()) {
            println("SQL Injection for beer name")
            return
        }
        _beerRepository.add(beer)
    }
}
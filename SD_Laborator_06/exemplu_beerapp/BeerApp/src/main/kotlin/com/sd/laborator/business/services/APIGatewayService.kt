package com.sd.laborator.business.services

import com.sd.laborator.business.interfaces.*
import com.sd.laborator.models.Beer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class APIGatewayService : IAPIGatewayService {
    @Autowired
    private lateinit var _createBeerService: ICreateBeerService
    @Autowired
    private lateinit var _readBeerService: IReadBeerService
    @Autowired
    private lateinit var _updateBeerService: IUpdateBeerService
    @Autowired
    private lateinit var _deleteBeerService: IDeleteBeerService

    override fun gatewayResult(operation : String, beer: Beer?, name: String?, price: Float?): Any?{
        return when(operation) {
            "createBeerTable" -> _createBeerService.createBeerTable()
            "addBeer" -> _createBeerService.addBeer(beer!!)
            "getBeers" -> _readBeerService.getBeers()
            "getBeerByName" -> _readBeerService.getBeerByName(name!!)
            "getBeerByPrice" -> _readBeerService.getBeerByPrice(price!!)
            "updateBeer" -> _updateBeerService.updateBeer(beer!!)
            "deleteBeer" -> _deleteBeerService.deleteBeer(name!!)
            else -> null
        }
    }
}
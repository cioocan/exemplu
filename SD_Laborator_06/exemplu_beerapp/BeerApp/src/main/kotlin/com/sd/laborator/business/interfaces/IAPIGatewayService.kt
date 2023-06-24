package com.sd.laborator.business.interfaces

import com.sd.laborator.models.Beer

interface IAPIGatewayService {
    fun gatewayResult(operation : String, beer: Beer?, name: String?, price: Float?): Any?
}
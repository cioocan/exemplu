package com.sd.laborator.business.interfaces

import com.sd.laborator.models.Beer

interface IUpdateBeerService {
    fun updateBeer(beer: Beer)
}
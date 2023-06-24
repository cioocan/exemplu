package com.sd.laborator.business.interfaces

interface IReadBeerService {
    fun getBeers(): String
    fun getBeerByName(name: String): String?
    fun getBeerByPrice(price: Float): String
}
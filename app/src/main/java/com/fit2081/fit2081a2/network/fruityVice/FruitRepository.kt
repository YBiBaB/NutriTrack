package com.fit2081.fit2081a2.network.fruityVice

class FruitRepository {
    suspend fun getFruitByName(name: String): Fruit {
        return FruityViceApi.instance.getFruit(name)
    }
}
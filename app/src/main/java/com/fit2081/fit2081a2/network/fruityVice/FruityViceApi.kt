package com.fit2081.fit2081a2.network.fruityVice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FruityViceApi {
    @GET("api/fruit/{fruitName}")
    suspend fun getFruit(@Path("fruitName") fruitName: String): Fruit

    companion object {
        private const val BASE_URL = "https://fruityvice.com/"

        val instance: FruityViceApi by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FruityViceApi::class.java)
        }
    }
}
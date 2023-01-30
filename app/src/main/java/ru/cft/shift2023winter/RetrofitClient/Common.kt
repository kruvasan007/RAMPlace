package ru.cft.shift2023winter.RetrofitClient

object Common {
    private val BASE_URL = "https://rickandmortyapi.com/api/"
    val retrofitService: RetrofitService
    get() = RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)
}
package ru.cft.shift2023winter.RetrofitClient

import retrofit2.Call
import retrofit2.http.GET
import ru.cft.shift2023winter.Model.SearchResult

interface RetrofitService {
    @GET("character")
    fun getCharacterList(): Call<SearchResult>
}
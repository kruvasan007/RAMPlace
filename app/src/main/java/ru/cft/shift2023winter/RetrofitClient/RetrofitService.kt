package ru.cft.shift2023winter.RetrofitClient

import retrofit2.Response
import retrofit2.http.GET
import ru.cft.shift2023winter.Model.SearchResult

interface RetrofitService {
    @GET("?page=2")
    suspend fun getCharacterList(): Response<SearchResult>
}
package ru.cft.shift2023winter.RetrofitClient

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.cft.shift2023winter.Model.Locations
import ru.cft.shift2023winter.Model.SearchLocationResult

interface RetrofitService {

    @GET("character/{id}")
    suspend fun getCharacterListById(@Path("id") id: String): Response<List<ru.cft.shift2023winter.Model.Character>>

    @GET("location/{id}")
    suspend fun getLocationList(@Path("id") id: String): Response<List<Locations>>
}
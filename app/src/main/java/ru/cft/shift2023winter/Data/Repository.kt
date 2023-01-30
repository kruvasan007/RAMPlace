package ru.cft.shift2023winter.Data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import ru.cft.shift2023winter.Model.Locations
import ru.cft.shift2023winter.RetrofitClient.Common

object Repository {
    private val retrofitService = Common.retrofitService
    private var job: Job? = null
    private val locationsList = MutableLiveData<List<Locations>>()
    private val characterList = MutableLiveData<List<ru.cft.shift2023winter.Model.Character>>()
    val loadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    init {
        loadCharacters(1)
        loadLocations()
    }

    fun refresh() {
        loadLocations()
        loadCharacters(1)
    }

    fun getLocationList(): MutableLiveData<List<Locations>> {
        if (job == null) refresh()
        return locationsList
    }

    private fun loadLocations() {
        val id = (IntArray(120) { it + 1 }).contentToString()
        loading.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofitService.getLocationList(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    locationsList.value = response.body()
                    loadError.value = null
                    loading.value = false
                }
            }
        }
        loadError.value = ""
        loading.value = false
    }

    fun getCharacterList(): MutableLiveData<List<ru.cft.shift2023winter.Model.Character>> {
        if (job == null) refresh()
        return characterList
    }

    private fun loadCharacters(page: Int) {
        val id = (IntArray(400) { it + 1 }).contentToString()
        loading.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofitService.getCharacterListById(id.toString())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    characterList.value = response.body()
                    loadError.value = null
                    loading.value = false
                }
            }
        }
        loadError.value = ""
        loading.value = false
    }
}
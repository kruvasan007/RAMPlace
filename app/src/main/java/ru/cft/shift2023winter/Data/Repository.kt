package ru.cft.shift2023winter.Data

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import kotlinx.coroutines.*
import ru.cft.shift2023winter.App
import ru.cft.shift2023winter.DataBase.AppDatabase
import ru.cft.shift2023winter.DataBase.CharacterDao
import ru.cft.shift2023winter.DataBase.CharacterDto
import ru.cft.shift2023winter.Model.Locations
import ru.cft.shift2023winter.RetrofitClient.Common

object Repository {
    private val retrofitService = Common.retrofitService
    private var job: Job? = null
    private val locationsList = MutableLiveData<List<Locations>>()
    private val characterList = MutableLiveData<List<ru.cft.shift2023winter.Model.Character>>()
    private val likeCharacters = MutableLiveData<List<CharacterDto>>()
    private val characterDao: CharacterDao = App.getDatabase()!!.characterDao()
    val loadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    init {
        loadLikes()
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

    fun getLikes() = likeCharacters

    fun setLike(char: CharacterDto){
        characterDao.insertCharacter(char)
        loadLikes()
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

    fun loadLikes() {
        job = CoroutineScope(Dispatchers.Main).launch {
            likeCharacters.value = characterDao.getAll()
        }
    }

    fun deleteLike(character: CharacterDto) {
        characterDao.delete(character)
        loadLikes()
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
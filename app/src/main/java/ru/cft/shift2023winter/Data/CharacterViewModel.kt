package ru.cft.shift2023winter.Data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.cft.shift2023winter.DescriptionFragmentArgs
import ru.cft.shift2023winter.RetrofitClient.Common

class CharacterViewModel : ViewModel() {
    val retrofitService = Common.retrofitService
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val characterList = MutableLiveData<List<ru.cft.shift2023winter.Model.Character>>()
    val loadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        loadCharacters()
    }

    fun getCharacterList(): MutableLiveData<List<ru.cft.shift2023winter.Model.Character>> {
        if (job == null) refresh()
        return characterList
    }

    fun getCharacterById(id: Int) = characterList.value?.get(id)

    fun getCountCharacter() = characterList.value?.size

    private fun loadCharacters() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofitService.getCharacterList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    characterList.value = response.body()?.results
                    loadError.value = null
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        loadError.value = ""
        loading.value = false
    }

    private fun onError(message: String) {
        loadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
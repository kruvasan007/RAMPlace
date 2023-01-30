package ru.cft.shift2023winter.Data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.cft.shift2023winter.RetrofitClient.Common

class CharacterViewModel : ViewModel() {
    private var characterList = Repository.getCharacterList()

    private fun refresh() {
        Repository.refresh()
    }

    fun getCharacterList() = characterList

    fun getCharacterById(id: Int) = characterList.value?.get(id)

    fun getCountCharacter() = characterList.value?.size
}
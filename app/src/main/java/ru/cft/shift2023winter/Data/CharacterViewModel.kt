package ru.cft.shift2023winter.Data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.cft.shift2023winter.DataBase.CharacterDto
import ru.cft.shift2023winter.RetrofitClient.Common

class CharacterViewModel : ViewModel() {
    private var characterList = Repository.getCharacterList()
    private var likesList = Repository.getLikes()

    private fun refresh() {
        Repository.refresh()
    }

    fun getCharacterList() = characterList

    fun getLikes() = likesList

    fun findInsertionLikes(character: ru.cft.shift2023winter.Model.Character) = likesList.value!!.contains(convertCharacter(character))

    fun deleteLike(character: ru.cft.shift2023winter.Model.Character) {
        Repository.deleteLike(convertCharacter(character))
    }

    fun setLike(character: ru.cft.shift2023winter.Model.Character): Boolean{
        if(likesList.value!!.contains(convertCharacter(character))) return false
        Repository.setLike(convertCharacter(character))
        return true
    }

    private fun convertCharacter(character: ru.cft.shift2023winter.Model.Character) = CharacterDto(character.id, character.name, character.status, character.image)
    fun getCharacterById(id: Int) = characterList.value?.get(id)

    fun getCountCharacter() = characterList.value?.size
}
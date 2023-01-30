package ru.cft.shift2023winter.Data

import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    private var locationsList = Repository.getLocationList()

    fun refresh() {
        Repository.refresh()
    }

    fun getLocationList() = locationsList
}
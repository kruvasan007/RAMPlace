package ru.cft.shift2023winter.Model

data class Character(
    var id: Int? = null,
    var name: String? = null,
    var status: String? = null,
    var species: String? = null,
    var type: String? = null,
    var gender: String? = null,
    var origin: ObjectJSON? = null,
    var location: Location? = null,
    var image: String? = null,
    var episode: List<String>? = null,
    var url: String? = null,
    var created: String? = null,
)

data class Location(
    var name: String? = null,
    var url: String? = null,
)

data class ObjectJSON(
    var name: String? = null,
    var url: String? = null,
)
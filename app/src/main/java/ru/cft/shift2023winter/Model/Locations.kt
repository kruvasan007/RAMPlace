package ru.cft.shift2023winter.Model

data class SearchLocationResult(
    var info: Info,
    var results: List<Locations>
)

data class Info(
    var count: Int? = null,
    var pages: Int? = null,
    var next: String? = null,
    var prev: String? = null,
)

data class Locations(
    var id: Int,
    var name: String,
    var type: String,
    var dimension: String,
    var residents: List<String>,
    var characters: List<Character>? = null,
    var url: String,
    var created: String,
)
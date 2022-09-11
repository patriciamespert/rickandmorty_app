package com.mundoandroid.rickandmortyrecyclerview.model

data class Result(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    var type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode:List<String>,
    val url: String = "",
    val created: String = ""
)
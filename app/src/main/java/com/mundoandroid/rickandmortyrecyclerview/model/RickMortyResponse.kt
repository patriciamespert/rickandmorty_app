package com.mundoandroid.rickandmortyrecyclerview.model

import com.google.gson.annotations.SerializedName

data class RickMortyResponse(
    @SerializedName("info") var info:Info,
    @SerializedName("results") var images: List<Result>,
)
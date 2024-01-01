package com.elmerdev.superheroapp

import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(
  @SerializedName("response") val response: String,
  @SerializedName("results-for") val resultsFor:String,
  @SerializedName("results") val superheroes: List<SuperHeroItemResponse>
)

data class SuperHeroItemResponse(
  @SerializedName("id") val id: String,
  @SerializedName("name") val name: String,
  @SerializedName("image") val image:SuperHeroImageResponse
)

data class SuperHeroImageResponse(
  @SerializedName("url") val url:String
)
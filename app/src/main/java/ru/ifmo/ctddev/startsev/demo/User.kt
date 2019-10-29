package ru.ifmo.ctddev.startsev.demo

import com.squareup.moshi.Json

class User(val firstName: String, val lastName: String)

data class GitHubRepo(
    val name: String,
    val description: String? = "",
    @Json(name = "stargazers_count") val starsCount: Int
)



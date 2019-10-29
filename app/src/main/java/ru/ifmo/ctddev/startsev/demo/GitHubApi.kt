package ru.ifmo.ctddev.startsev.demo

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface GitHubApi {
    @GET("users/{user}/repos")
    fun getRepos(@Path("user") userName: String, @Query("sort") sort: String = "updated"): Call<List<GitHubRepo>>
}

fun createGithubApi(): GitHubApi {
    val client = OkHttpClient()
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("https://api.github.com/")
        .build()
    val api = retrofit.create(GitHubApi::class.java)
    return api
}

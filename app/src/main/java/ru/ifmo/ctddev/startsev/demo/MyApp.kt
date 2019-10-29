package ru.ifmo.ctddev.startsev.demo

import android.app.Application

class MyApp : Application() {
    lateinit var githubApi: GitHubApi
        private set

    override fun onCreate() {
        super.onCreate()
        val api = createGithubApi()
        githubApi = api
        app = this
    }

    companion object {
        lateinit var app: MyApp
            private set
    }
}

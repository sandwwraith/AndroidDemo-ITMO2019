package ru.ifmo.ctddev.startsev.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private val LOG_TAG = "GitHub API"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setup()
    }

    private lateinit var adapter: Repos

    private fun setup() {
        val viewManager = LinearLayoutManager(this)
        adapter = Repos(emptyList()) {}
        my_recycler_view.apply {
            layoutManager = viewManager
            adapter = this@MainActivity.adapter
            setHasFixedSize(true)
        }
        run()
    }

    private var call: Call<List<GitHubRepo>>? = null

    private fun run() {
        call = MyApp.app.githubApi.getRepos("Kotlin")
        call?.enqueue(object : Callback<List<GitHubRepo>> {
            override fun onFailure(call: Call<List<GitHubRepo>>, t: Throwable) {
                Log.e(LOG_TAG, "Failed with", t)
            }

            override fun onResponse(call: Call<List<GitHubRepo>>, response: Response<List<GitHubRepo>>) {
                Log.d(LOG_TAG, Thread.currentThread().name)
                val body = response.body()
                Log.d(LOG_TAG, "Finished with ${response.code()}, body: $body")
                adapter.contacts = body ?: emptyList()
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        call?.cancel()
        call = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

package ru.ifmo.ctddev.startsev.demo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.*
import java.io.IOException

private const val myRequestId = 42

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, // Контекст
                arrayOf(Manifest.permission.READ_CONTACTS), // Что спрашиваем
                myRequestId
            ) // Пользовательская константа для уникальности запроса
        } else {
            setup()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            myRequestId -> {
                // grantResults пуст, если пользователь отменил диалог
                // (но не согласился или отказался)
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Победа
                    setup()
                } else {
                    Toast.makeText(this, "Grant me permissions!", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private val client = OkHttpClient()
    private var call: Call? = null

    private fun run() {
        val request = Request.Builder()
            .get()
            .url("https://api.github.com/users/sandwwraith/repos?sort=updated")
            .addHeader("Accept", "application/json")
            .build()

        call = client.newCall(request)
        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("GitHub API", "Failed with", e)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("GitHub API", "Finished with ${response.code}, body: ${response.body?.charStream()?.readText()}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        call?.cancel()
        call = null
    }

    private fun setup() {
//        val contacts = fetchAllContacts()
        run()
        val contacts = (10..40).map { Contact("Ivan Ivanov", "+7 999 123 12 $it") }.shuffled()
        val viewManager = LinearLayoutManager(this)
        my_recycler_view.apply {
            layoutManager = viewManager
            adapter = Contacts(contacts) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_DIAL
                    data = Uri.parse("tel:${it.phoneNumber}")
                }
                startActivity(sendIntent)
            }
            setHasFixedSize(true)
        }
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

package ru.ifmo.ctddev.startsev.demo

import android.content.Context
import android.provider.ContactsContract

class User(val firstName: String, val lastName: String)

val usersList = (0..30).map {
    User("First name #$it", "Last name #$it")
}


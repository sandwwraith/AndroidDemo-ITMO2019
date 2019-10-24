package ru.ifmo.ctddev.startsev.demo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

class ContactsVH(val root: View) : RecyclerView.ViewHolder(root) {
    val userFirstNameText = root.user_firstname
    val userLastNameText = root.user_lastname
}

class Contacts(val contacts: List<Contact>, val onClick: (Contact) -> Unit) : RecyclerView.Adapter<ContactsVH>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsVH {
        val holder = ContactsVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
        holder.root.setOnClickListener {
            onClick(contacts[holder.adapterPosition])
        }
        return holder
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactsVH, position: Int) {
        holder.userFirstNameText.text = contacts[position].phoneNumber
        holder.userLastNameText.text = contacts[position].name
    }
}

val a = Contacts(listOf(), {})

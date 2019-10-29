package ru.ifmo.ctddev.startsev.demo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

class ReposVH(val root: View) : RecyclerView.ViewHolder(root) {
    val lastText = root.last_text
    val firstText = root.first_text
}

class Repos(var contacts: List<GitHubRepo>, val onClick: (GitHubRepo) -> Unit) : RecyclerView.Adapter<ReposVH>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReposVH {
        val holder = ReposVH(
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

    override fun onBindViewHolder(holder: ReposVH, position: Int) {
        holder.firstText.text = contacts[position].name
        holder.lastText.text = contacts[position].description
    }
}

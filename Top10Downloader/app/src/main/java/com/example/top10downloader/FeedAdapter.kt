package com.example.top10downloader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FeedAdapter(context: Context, private val applications: ArrayList<FeedEntry>) :
    ArrayAdapter<FeedEntry>(context, -1, applications) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.list_item, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val feedEntry = applications[position]
        holder.nameTextView.text = feedEntry.name
        holder.artistTextView.text = feedEntry.artist
        holder.summaryTextView.text = feedEntry.summary

        return view
    }

    companion object {
        private class ViewHolder(view: View) {
            val nameTextView: TextView = view.findViewById(R.id.nameTextView)
            val artistTextView: TextView = view.findViewById(R.id.artistTextView)
            val summaryTextView: TextView = view.findViewById(R.id.summaryTextView)
        }
    }
}
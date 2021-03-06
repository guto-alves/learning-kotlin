package com.example.top10downloader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FeedAdapter(
    context: Context,
    private var feedEntries: List<FeedEntry> = ArrayList()
) :
    ArrayAdapter<FeedEntry>(context, -1) {

    fun setFeedEntries(feedEntries: List<FeedEntry>) {
        this.feedEntries = feedEntries
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return feedEntries.size
    }

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

        val feedEntry = feedEntries[position]
        holder.numberTextView.text = "${position + 1}°"
        holder.nameTextView.text = feedEntry.name
        holder.artistTextView.text = feedEntry.artist
        holder.summaryTextView.text = feedEntry.summary

        return view
    }

    companion object {
        private class ViewHolder(view: View) {
            val numberTextView: TextView = view.findViewById(R.id.numberTextView)
            val nameTextView: TextView = view.findViewById(R.id.nameTextView)
            val artistTextView: TextView = view.findViewById(R.id.artistTextView)
            val summaryTextView: TextView = view.findViewById(R.id.summaryTextView)
        }
    }
}
package com.example.top10downloader

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.downloadUrl()

        val adapter = FeedAdapter(this)
        feedEntriesListView.adapter = adapter

        viewModel.feedEntries.observe(this, Observer<List<FeedEntry>>(adapter::setFeedEntries))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)

        if (viewModel.feedLimit == 25) {
            menu?.findItem(R.id.top25)?.isChecked = true
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.free_apps -> viewModel.feedUrl = FeedEntry.FREE_APPS_URL
            R.id.paid_apps -> viewModel.feedUrl = FeedEntry.PAID_APPS_URL
            R.id.songs -> viewModel.feedUrl = FeedEntry.SONGS_URL
            R.id.top10, R.id.top25 -> {
                if (!item.isChecked) {
                    item.isChecked = !item.isChecked
                    viewModel.switchLimit()
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }

        viewModel.downloadUrl()
        return true
    }
}
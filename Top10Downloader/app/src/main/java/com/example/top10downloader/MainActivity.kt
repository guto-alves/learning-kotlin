package com.example.top10downloader

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var feedUrl = FREE_APPS_URL
    private var feedLimit = 10
    private var feedUrlCached = "INVALIDATED"

    private var downloadTask: DownloadTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                feedUrl = getString(STATE_URL, FREE_APPS_URL)
                feedLimit = getInt(STATE_LIMIT)
            }
        }

        downloadUrl(feedUrl.format(feedLimit))
    }

    private fun downloadUrl(url: String) {
        if (url != feedUrlCached) {
            feedUrlCached = url
            downloadTask = DownloadTask(this, appsListView)
            downloadTask?.execute(url)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)

        if (feedLimit == 25) {
            menu?.findItem(R.id.top25)?.isChecked = true
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.free_apps -> feedUrl = FREE_APPS_URL
            R.id.paid_apps -> feedUrl = PAID_APPS_URL
            R.id.songs -> feedUrl = SONGS_URL
            R.id.top10, R.id.top25 -> {
                if (!item.isChecked) {
                    item.isChecked = !item.isChecked
                    feedLimit = 35 - feedLimit
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }

        downloadUrl(feedUrl.format(feedLimit))
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString(STATE_URL, feedUrl)
            putInt(STATE_LIMIT, feedLimit)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        downloadTask?.cancel(true)
    }

    companion object {
        private const val FREE_APPS_URL =
            "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
        private const val PAID_APPS_URL =
            "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml"
        private const val SONGS_URL =
            "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"

        private const val STATE_URL = "feedUrl"
        private const val STATE_LIMIT = "feedLimit"

        private class DownloadTask(val context: Context, val listView: ListView) :
            AsyncTask<String, Void, String>() {

            override fun doInBackground(vararg urls: String?): String {
                return downloadXml(urls[0].toString())
            }

            override fun onPostExecute(result: String) {
                val parse = ParseApplications()
                val adapter = FeedAdapter(context, parse.parse(result))
                listView.adapter = adapter
            }

            private fun downloadXml(urlPath: String) = URL(urlPath).readText()
        }
    }
}
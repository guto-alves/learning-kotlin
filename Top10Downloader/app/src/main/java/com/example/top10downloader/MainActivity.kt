package com.example.top10downloader

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val downloadData by lazy { DownloadDataTask(this, appsListView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
    }

    companion object {
        private class DownloadDataTask(val context: Context, val listView: ListView) :
            AsyncTask<String, Void, String>() {

            override fun doInBackground(vararg urls: String?): String {
                return downloadXml(urls[0].toString())
            }

            override fun onPostExecute(result: String) {
                val parse = ParseApplications()
                val adapter = FeedAdapter(context, parse.parse(result))
                listView.adapter = adapter
            }

            private fun downloadXml(urlPath: String): String {
                return URL(urlPath).readText()
            }
        }
    }
}
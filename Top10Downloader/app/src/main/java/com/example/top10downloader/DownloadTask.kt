package com.example.top10downloader

import android.os.AsyncTask
import java.net.URL

class DownloadTask(private val callback: DownloadTaskListener) : AsyncTask<String, Void, String>() {

    interface DownloadTaskListener {
        fun onDataAvailable(data: String)
    }

    override fun doInBackground(vararg urls: String?): String {
        return downloadXml(urls[0].toString())
    }

    override fun onPostExecute(result: String) {
        callback.onDataAvailable(result)
    }

    private fun downloadXml(urlPath: String): String {
        try {
            return URL(urlPath).readText()
        } catch (e: Exception) {
        }

        return ""
    }
}
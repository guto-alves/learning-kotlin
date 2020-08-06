package com.example.top10downloader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeedViewModel : ViewModel(), DownloadTask.DownloadTaskListener {
    private val _feedEntries = MutableLiveData<List<FeedEntry>>()
    val feedEntries: LiveData<List<FeedEntry>>
        get() = _feedEntries

    var feedUrl = FeedEntry.FREE_APPS_URL
    var feedLimit = 10
    private var feedUrlCached = ""
    private var downloadTask: DownloadTask? = null

    fun downloadUrl() {
        val url = feedUrl.format(feedLimit)

        if (url != feedUrlCached) {
            feedUrlCached = url
            downloadTask = DownloadTask(this)
            downloadTask?.execute(url)
        }
    }

    fun switchLimit() {
        feedLimit = 35 - feedLimit
    }

    override fun onDataAvailable(data: String) {
        _feedEntries.value = XmlParser.parse(data)
    }

    override fun onCleared() {
        downloadTask?.cancel(true)
    }
}
package com.example.top10downloader

class FeedEntry {
    var name = ""
    var artist = ""
    var releaseDate = ""
    var summary = ""
    var imageUrl = ""

    override fun toString(): String {
        return "FeedEntry(name='$name', artist='$artist')"
    }

    companion object {
        const val FREE_APPS_URL =
            "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
        const val PAID_APPS_URL =
            "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml"
        const val SONGS_URL =
            "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"
    }
}
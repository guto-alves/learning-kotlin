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
}
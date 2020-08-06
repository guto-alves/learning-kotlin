package com.example.top10downloader

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class XmlParser {

    companion object {
        fun parse(xml: String): List<FeedEntry> {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true

            val xpp = factory.newPullParser()
            xpp.setInput(xml.reader())

            var eventType = xpp.eventType

            val entries = ArrayList<FeedEntry>()
            var currentEntry = FeedEntry()
            var inEntry = false
            var text = ""

            try {
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    val tag = xpp.name

                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            if (xpp.name == "entry") {
                                inEntry = true
                            }
                        }
                        XmlPullParser.END_TAG -> {
                            if (inEntry) {
                                when (tag) {
                                    "entry" -> {
                                        entries.add(currentEntry)
                                        currentEntry = FeedEntry()
                                        inEntry = false
                                    }
                                    "name" -> currentEntry.name = text
                                    "artist" -> currentEntry.artist = text
                                    "releaseDate" -> currentEntry.releaseDate = text
                                    "summary" -> currentEntry.summary = text
                                    "image" -> currentEntry.imageUrl = text
                                }
                            }
                        }
                        XmlPullParser.TEXT -> text = xpp.text
                    }
                    eventType = xpp.next()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return entries
        }
    }
}
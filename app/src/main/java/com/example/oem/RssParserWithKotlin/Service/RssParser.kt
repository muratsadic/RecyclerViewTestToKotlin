package com.example.oem.RssParserWithKotlin.Service

/*
  Created by halit on 22.05.2016.
 */

import android.util.Xml


import com.example.oem.RssParserWithKotlin.RssItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

import java.io.IOException
import java.io.InputStream
import java.text.ParseException
import java.util.ArrayList


class RssParser {
    private val nameSpace: String? = null //No name space

    internal var aaa: String? = null

    @Throws(XmlPullParserException::class, IOException::class, ParseException::class)
    fun parse(inputStream: InputStream?): List<RssItem>? {
        val parser = Xml.newPullParser()
        try {

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            try {
                return readFeed(parser)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }
        try {
            return readFeed(parser)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


    @Throws(XmlPullParserException::class, IOException::class, ParseException::class)
    fun readFeed(parser: XmlPullParser): List<RssItem> {
        var channel_title: String? = null
        var title: String? = null
        var link: String? = null
        var pubDate: String? = null
        var description: Document? = null
        var insideItem: Boolean? = false
        var eventType = parser.eventType
        val items = ArrayList<RssItem>()

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.name.equals("item", ignoreCase = true)) {
                    insideItem = true
                } else if (parser.name.equals("title", ignoreCase = true)) {
                    if (insideItem!!)
                        title = readTitle(parser) //extract the title of article
                    else {
                        channel_title = "<< " + readTitle(parser) + " >>" // extract channel title
                    }
                } else if (parser.name.equals("link", ignoreCase = true)) {
                    if (insideItem!!)
                        link = readLink(parser) //extract the link of article
                } else if (parser.name.equals("pubDate", ignoreCase = true)) {
                    if (insideItem!!)
                        pubDate = readPubDate(parser) //extract the link of article
                } else if (parser.name.equals("description", ignoreCase = true)) {
                    if (insideItem!!)
                        description = readDescription(parser) //extract the link of article
                }

            } else if (eventType == XmlPullParser.END_TAG && parser.name.equals("item", ignoreCase = true)) {
                insideItem = false
            }
            eventType = parser.next()

            if (channel_title != null && title != null && link != null && pubDate != null && description != null) {
                val item = RssItem(channel_title, title, link, pubDate, description)
                items.add(item)
                title = null
                link = null
                pubDate = null
                description = null


            }
            //  Toast.makeText(this, String.valueOf(item.getTitle()), Toast.LENGTH_SHORT).show();
        }
        return items
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readTitle(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, nameSpace, "title")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, nameSpace, "title")
        return title
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readLink(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, nameSpace, "link")
        val link = readText(parser)
        parser.require(XmlPullParser.END_TAG, nameSpace, "link")
        return link
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readPubDate(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, nameSpace, "pubDate")
        val pubDate = readText(parser).substring(0, 25)
        parser.require(XmlPullParser.END_TAG, nameSpace, "pubDate")
        return pubDate

    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readDescription(parser: XmlPullParser): Document {
        parser.require(XmlPullParser.START_TAG, nameSpace, "description")
        val description = readText(parser)
        parser.require(XmlPullParser.END_TAG, nameSpace, "description")
        return Jsoup.parseBodyFragment(description)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }
}


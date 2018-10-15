package com.example.oem.RssParserWithKotlin

import org.jsoup.nodes.Document
import java.net.MalformedURLException
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RssItem : Comparable<RssItem>{


    var description: Document? = null
    var formattedDate: Date? = null
        internal set
    var channel_title: String? = null
    var title: String? = null
    var link: String? = null
    var pubDate: String? = null
    var image: String? = null

    constructor() {}

    constructor(channel_title: String, title: String, link: String, pubDate: String, description: Document) {
        this.channel_title = channel_title
        this.title = title
        this.link = link
        this.pubDate = pubDate
        formattedDate = changePubdateFormat(this.pubDate)
        this.description = description

    }
    val imageURL: String?
        get() {
            val img = description!!.getElementsByTag("img")
            val link = img.attr("src")
            var _link: URL? = null
            try {
                _link = URL(link)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }

            assert(_link != null)
            try {
                return _link!!.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null

        }
    override fun compareTo(other: RssItem): Int {
        return formattedDate!!.compareTo(other.formattedDate)
    }

    @Throws(ParseException::class)
    fun changePubdateFormat(pubDate: String?): Date {
        val formatter = SimpleDateFormat("EEE, dd MMMM yyyy HH:mm:ss", Locale.ENGLISH)
        val date = formatter.format(Date())
        println(date)
        return formatter.parse(this.pubDate)

    }
}

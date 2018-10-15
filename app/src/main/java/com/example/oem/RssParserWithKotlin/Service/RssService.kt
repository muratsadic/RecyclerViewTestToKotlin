package com.example.oem.RssParserWithKotlin.Service

/*
  Created by halit on 22.05.2016.
 */

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log


import com.example.oem.RssParserWithKotlin.RssItem

import org.xmlpull.v1.XmlPullParserException

import java.io.IOException
import java.io.InputStream
import java.io.Serializable
import java.net.URL
import java.text.ParseException
import java.util.ArrayList


class RssService : IntentService("RssService") {

    private var mStop = false
    protected var child: RssService? = null

    val sLock = Any()

    override fun onHandleIntent(intent: Intent?) {


        val rssItems = ArrayList<List<RssItem>>()
        try {
            val parser = RssParser()
            if (intent!!.getBooleanExtra(SHOW_ALL, false)) {
                for (link in intent.getStringArrayListExtra(LINK)) {
                    val single_link = parser.parse(getInputStream(link))
                    if(single_link==null){
                    }else{
                        rssItems.add(single_link!!)
                    }

                   //  while (rssItems.remove(false) );
                }
            } else {
                val single_link = parser.parse(getInputStream(intent.getStringArrayListExtra(LINK)[0]))
                rssItems.add(single_link!!)
                // while (rssItems.remove(null));
            }
        } catch (e: IOException) {
            Log.w(e.message, e)
        } catch (e: XmlPullParserException) {
            Log.w(e.message, e)
        } catch (e: ParseException) {
            Log.w(e.message, e)
        }


        val bundle = Bundle()
        bundle.putSerializable(ITEMS, rssItems as Serializable)
        val receiver = intent!!.getParcelableExtra<ResultReceiver>(RECEIVER)
        receiver.send(0, bundle)


    }

    fun getInputStream(link: String): InputStream? {
        try {
            val url = URL(link)
            return url.openConnection().getInputStream()
        } catch (e: IOException) {
            Log.w("RssApp", "Exception while retrieving the input stream", e)
            return null
        }

    }

    override fun onDestroy() {
        synchronized(sLock) {
            mStop = true
        }
    }

    companion object {

        val ITEMS = "items"
        val RECEIVER = "receiver"
        val LINK = "link"
        val SHOW_ALL = "all"
        val rssService = "RssService"
    }


}

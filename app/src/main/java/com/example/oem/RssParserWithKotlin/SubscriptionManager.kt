package com.example.oem.RssParserWithKotlin

import java.util.ArrayList

class SubscriptionManager private constructor() {

    fun getLink(position: Int): ArrayList<String> {
        val link = ArrayList<String>()
        try {
            link.add(all[position])
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return link
    }

    fun initList() {

        this.addLink("https://shiftdelete.net/rss-techapp") //0
        this.addLink("https://www.donanimhaber.com/Rss/Tum/Default.aspx") //1
       // this.addLink("http://feeds.feedburner.com/webteknocom") //3
        /* this.addLink("http://webrazzi.com/feed/"); //2
         this.addLink("http://www.teknokulis.com/rss/anasayfa.xml"); //3
         this.addLink("https://hwp.com.tr/feed"); //4
         this.addLink("https://www.log.com.tr/feed/"); //5
         this.addLink("https://servis.chip.com.tr/chiponline.xml"); //6
         this.addLink("http://www.donanimarsivi.com/feed/"); //7
         this.addLink("https://teknoseyir.com/feed"); //8
         this.addLink("http://www.pcworld.com.tr/feed/"); // 9
         this.addLink("https://teknodiot.com/feed/"); //10
         this.addLink("http://www.computerworld.com.tr/feed/");//11
         this.addLink("http://www.teknoekip.com/feed"); // 12
         this.addLink("https://donanimgunlugu.com/feed/"); // 13*/


        // Hem Resim Hem Haber Çekilebilen Siteler//

        // http://www.computerworld.com.tr/feed/


        // Sadece Haber Çekilen Resim Çekilemeyen Siteler//

        // https://www.techinside.com/feed/
        // https://donanimgunlugu.com/feed/
        // http://www.teknoekip.com/feed


        // Alternatif ShiftDelete Adresi

        // this.addLink("http://teknolojituru.com/feed/");


        // this.addLink("http://www.donanimarsivi.com/feed/");


        // https://shiftdelete.net/feed


        // this.addLink("http://feeds.feedburner.com/technopat");

        // this.addLink("http://feeds.feedburner.com/IndircomHaberler");

        // this.addLink("http://technosfer.com/feed/");

        // this.addLink("http://www.tamindir.com/haber/rss");
        // this.addLink("http://blog.tamindir.com/rss");

        // this.addLink("http://pchocasi.com.tr/feed/");
        // this.addLink("http://www.webtekno.com/rss.xml");
        // this.addLink("http://www.enpedi.com/feeds/posts/default?alt=rss");
        // this.addLink("http://www.pcworld.com.tr/feed/");


    }


    fun addLink(link: String) {
        SubscriptionManager.all.add(link)
    }

    companion object {


        val all = ArrayList<String>()

        val instance = SubscriptionManager()
    }
}

package com.example.oem.RssParserWithKotlin


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.oem.RssParserWithKotlin.Service.RssService
import com.example.oem.RssParserWithKotlin.SubscriptionManager.Companion.all
import java.util.*

class MainActivity : AppCompatActivity() {


    var subscriptionManager = SubscriptionManager.instance
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter

    companion object {

        private var appendinglist: ArrayList<RssItem> = ArrayList()
        private lateinit var linearLayoutManager: LinearLayoutManager
        private var links: ArrayList<String> = ArrayList()

    }

    private val resultReceiver = object : ResultReceiver(Handler()) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {


            val receivedPackage = resultData.getSerializable(RssService.ITEMS) as List<List<RssItem>>
            for (a in receivedPackage) {
                for (b in a) {
                    appendinglist.add(b)

                }
            }
            Collections.sort(appendinglist)
            Collections.reverse(appendinglist)

            adapter = RecyclerAdapter(applicationContext, appendinglist)
            adapter.notifyDataSetChanged()
            recyclerView!!.adapter = adapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        links = all
        recyclerView = findViewById(R.id.recyclerView)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        subscriptionManager.initList()
        startService()

    }

    private fun startService() {

        val intent = Intent(applicationContext, RssService::class.java)
        intent.putExtra(RssService.RECEIVER, resultReceiver)
        intent.putExtra(RssService.LINK, links)
        intent.putExtra(RssService.SHOW_ALL, true)
        startService(intent)


    }
}



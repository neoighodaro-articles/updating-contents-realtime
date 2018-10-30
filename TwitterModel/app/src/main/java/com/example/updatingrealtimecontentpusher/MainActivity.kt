package com.example.updatingrealtimecontentpusher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.pusher.client.channel.SubscriptionEventListener
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
	
	private val newList = ArrayList<String>()
	private val recyclerListAdapter = RecyclerListAdapter()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setupPusher()
		setupRecyclerView()
		setupClickListeners()
	}
	

	
	private fun setupRecyclerView() {
	
		with(recyclerViewContents){
			layoutManager = LinearLayoutManager(this@MainActivity)
			adapter = recyclerListAdapter
		}
		recyclerListAdapter.addItem("Hello World")
		recyclerListAdapter.addItem("New article alert!")
	
	}
	
	private fun setupClickListeners() {
		
		textViewNewContents.setOnClickListener {
			for (item in newList){
				recyclerListAdapter.addItem(item)
			}
			textViewNewContents.visibility = View.GONE
		}
		
	}
	
	private fun setupPusher() {
		val options = PusherOptions()
		options.setCluster("PUSHER_CLUSTER")
		val pusher = Pusher("PUSHER_KEY", options)
		
		val channel = pusher.subscribe("my-channel")
		
		channel.bind("my-event") { channelName, eventName, data ->
			runOnUiThread {
				textViewNewContents.visibility = View.VISIBLE
				newList.add(JSONObject(data).getString("message"))
			}
			
		}
		
		pusher.connect()
	}
}

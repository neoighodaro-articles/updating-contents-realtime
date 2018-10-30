package com.example.whatsappmodel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), RecyclerListAdapter.OnLastPositionReached {
	
	private var lastPosition = false
	override fun otherPosition() {
		lastPosition = false
	}
	
	override fun lastPositionReached() {
		lastPosition = true
		textViewNewContents.visibility = View.GONE
	}
	
	private var count = 0
	private val recyclerListAdapter = RecyclerListAdapter(this)
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setupClickListeners()
		setupRecyclerView()
		setupPusher()
		
	}
	
	private fun setupRecyclerView() {
		
		with(recyclerViewContents){
			layoutManager = LinearLayoutManager(this@MainActivity)
			adapter = recyclerListAdapter
		}
		recyclerListAdapter.addItem("Hello World")
		recyclerListAdapter.addItem("New article alert!")
		recyclerListAdapter.addItem("Pusher is actually awesome")
		recyclerListAdapter.addItem("Realtime functionality freely provided ")
		recyclerListAdapter.addItem("Checkout pusher.com/tutorials")
		recyclerListAdapter.addItem("You can also checkout blog.pusher.com")
		recyclerListAdapter.addItem("Learn how to update contents properly ")
		recyclerListAdapter.addItem("Hello World")
		recyclerListAdapter.addItem("New article alert!")
		recyclerListAdapter.addItem("Pusher is actually awesome")
		recyclerListAdapter.addItem("Realtime functionality freely provided ")
		recyclerListAdapter.addItem("Checkout pusher.com/tutorials")
		recyclerListAdapter.addItem("You can also checkout blog.pusher.com")
		recyclerListAdapter.addItem("Learn how to update contents properly ")
		
		
	}
	
	private fun setupClickListeners() {
		
		textViewNewContents.setOnClickListener {
			recyclerViewContents.scrollToPosition(recyclerListAdapter.itemCount-1)
			textViewNewContents.visibility = View.GONE
			count = 0
		}
		
	}
	
	private fun setupPusher() {
		val options = PusherOptions()
		options.setCluster("PUSHER_CLUSTER")
		val pusher = Pusher("PUSHER_KEY", options)
		
		val channel = pusher.subscribe("my-channel")
		
		channel.bind("my-event") { channelName, eventName, data ->
			
			runOnUiThread {
				
				if (!lastPosition){
					count ++
					textViewNewContents.text = count.toString()
					textViewNewContents.visibility = View.VISIBLE
					recyclerListAdapter.addItem(JSONObject(data).getString("message"))
					
				} else {
					recyclerListAdapter.addItem(JSONObject(data).getString("message"))
					recyclerViewContents.scrollToPosition(recyclerListAdapter.itemCount-1)
					
				}
				
				
			}
			
		}
		
		pusher.connect()
	}
	
}

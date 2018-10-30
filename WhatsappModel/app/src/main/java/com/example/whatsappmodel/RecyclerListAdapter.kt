package com.example.whatsappmodel

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Idorenyin Obong on 18/10/2018
 *
 */

class RecyclerListAdapter(private val listener:OnLastPositionReached): RecyclerView.Adapter<RecyclerListAdapter.ViewHolder>() {
	
	private val contentList: ArrayList<String> = ArrayList()
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(
			LayoutInflater.from(parent.context)
				.inflate(android.R.layout.simple_list_item_1, parent, false))
	}
	
	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(contentList[position])
		if (position==contentList.size-1){
			listener.lastPositionReached()
		} else {
			listener.otherPosition()
		}
	}
	
	override fun getItemCount(): Int = contentList.size
	
	fun addItem(item:String){
		contentList.add(item)
		notifyDataSetChanged()
	}
	
	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		
		private val userName: TextView = itemView.findViewById(android.R.id.text1)
		
		fun bind(item: String) = with(itemView) {
			userName.text = item
		}
		
	}
	
	interface OnLastPositionReached {
		fun lastPositionReached()
		fun otherPosition()
	}
	
}
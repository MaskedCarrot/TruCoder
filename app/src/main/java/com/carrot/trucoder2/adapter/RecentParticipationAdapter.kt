package com.carrot.trucoder2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carrot.trucoder2.R
import com.carrot.trucoder2.model.RecentParticipation

class RecentParticipationAdapter : RecyclerView.Adapter<RecentParticipationAdapter.RecentViewHolder>() {

    var list :List<RecentParticipation> = ArrayList()

    class RecentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var event = itemView.findViewById<TextView>(R.id.recent_particpation_contest_name)
        var rank = itemView.findViewById<TextView>(R.id.recent_particpation_recycle_rank)
        var Rating = itemView.findViewById<TextView>(R.id.recent_particpation_recycle_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vh_recentparticipation , parent , false)
        return RecentViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.event.text = list[position].event
        var temp = "Rating :${list[position].rating.toString()}"
        holder.Rating.text = temp
        temp =  "Rank :${list[position].rank}"
        holder.rank.text = temp

    }

    override fun getItemCount() = list.size

    fun submitList(l:List<RecentParticipation>){
        list = l.reversed()
    }
}
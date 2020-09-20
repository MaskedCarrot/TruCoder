package com.carrot.trucoder2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carrot.trucoder2.R
import com.carrot.trucoder2.model.Leaderboard
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.coroutines.coroutineContext

class LeaderboardAdapter(val context:Context) : RecyclerView.Adapter<LeaderboardAdapter.RecentViewHolder>() {

    var list :List<Leaderboard> = ArrayList()

    class RecentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var event = itemView.findViewById<TextView>(R.id.friends_handle)
        var Rating = itemView.findViewById<TextView>(R.id.friends_rating)
        var Badge = itemView.findViewById<ImageView>(R.id.friends_badge)
        var Photo = itemView.findViewById<CircleImageView>(R.id.friends_photo)
        var Position = itemView.findViewById<TextView>(R.id.friends_postiton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vh_leaderboard, parent , false)
        return RecentViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.event.text = list[position].Name
        holder.Rating.text = list[position].rating.toString()
        if(list[position].platform == 1)
            Glide.with(context).load(list[position].avatar).into(holder.Photo)
        else
            holder.Photo.setImageResource(R.drawable.codechef)
        when(position){
            0->holder.Badge.setImageResource(R.drawable.ic_medal_gold)
            1->holder.Badge.setImageResource(R.drawable.ic_medal_silver)
            2->holder.Badge.setImageResource(R.drawable.ic_medal_bronze)
        }
        holder.Position.text = (position+1).toString()

    }

    override fun getItemCount() = list.size

    fun submitList(l:List<Leaderboard>){
        list = l
        notifyDataSetChanged()
    }
}
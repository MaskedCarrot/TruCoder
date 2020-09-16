package com.carrot.trucoder2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.carrot.trucoder2.R
import com.carrot.trucoder2.model.ResultContest
import com.carrot.trucoder2.utils.Functions.Companion.ConvertDateFromMill
import com.carrot.trucoder2.utils.Functions.Companion.ConvertTimeFomMill

class FragmentSubContestRecycle(private val listener: (ResultContest) -> Unit) : RecyclerView.Adapter<FragmentSubContestRecycle.MyViewHolder>() {

    var oldList : List<ResultContest> = ArrayList()
    var list : List<ResultContest> = ArrayList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vh_subcontest , parent , false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val StartDate = ConvertDateFromMill(list[position].timestamp)
        val StartTime = ConvertTimeFomMill(list[position].timestamp)
        holder.event.text = list[position].event
        holder.startDate.text = StartDate
        holder.startTime.text =StartTime
        when(list[position].id){
            1->holder.logo.setImageResource(R.drawable.codeforces)
            2->holder.logo.setImageResource(R.drawable.codechef)
            12->holder.logo.setImageResource(R.drawable.topcoder)
            35->holder.logo.setImageResource(R.drawable.google)
            93->holder.logo.setImageResource(R.drawable.atcoder)
        }
        holder.itemView.setOnClickListener{listener(list[position])}
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(newList: List<ResultContest>){
        oldList = list
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ContestItemCallback( newList , oldList)
        )
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var event = itemView.findViewById<TextView>(R.id.subcontestviewholder_contest_Name)
        var logo = itemView.findViewById<ImageView>(R.id.subcontestviewholder_contestLogo)
        var startDate = itemView.findViewById<TextView>(R.id.fragSubContest_startdatetext)
        var startTime = itemView.findViewById<TextView>(R.id.fragSubContest_starttimetext)

    }

    class ContestItemCallback(var newList: List<ResultContest>, var oldList: List<ResultContest>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].event == newList[newItemPosition].event
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].equals(newList[newItemPosition])
        }

    }

}
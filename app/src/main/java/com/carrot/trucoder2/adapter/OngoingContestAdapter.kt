package com.carrot.trucoder2.adapter

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.carrot.trucoder2.R
import com.carrot.trucoder2.model.ResultContest

class OngoingContestAdapter: RecyclerView.Adapter<OngoingContestAdapter.MyViewHolder>() {

    var oldList : List<ResultContest> = ArrayList()
    var list : List<ResultContest> = ArrayList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.vh_contest_ongoing,
            parent,
            false
        )
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.event.text = list[position].event
        when(list[position].id){
            1 -> holder.logo.setImageResource(R.drawable.logo_codeforces)
            2 -> holder.logo.setImageResource(R.drawable.logo_codechef)
            12 -> holder.logo.setImageResource(R.drawable.logo_topcoder)
            35 -> holder.logo.setImageResource(R.drawable.logo_google)

        }
        val time = System.currentTimeMillis()
        val stamp = list[position].endstamp*1000
        holder.c.cancel()
        holder.c = object :CountDownTimer((stamp-time).toLong() , 1000){
            override fun onTick(p0: Long) {
                var ms = p0
                val days = ms / 86400000
                ms%=86400000
                val hours  = ms /3600000
                ms %=3600000
                val minutes = ms / 60000
                ms %=60000
                val seconds: Long = ms / 1000
                ms %=1000
                var TS = ""
                if(days > 0)
                    TS= "$days days"
                if(hours > 0)
                    TS = "$TS $hours hours"
                if(minutes > 0)
                    TS = "$TS $minutes minutes"
                if(seconds >0)
                    TS = "$TS $seconds seconds"
                holder.timer.text =TS
            }

            override fun onFinish() {
                holder.timer.text = "OVER"
            }

        }
        holder.c.start()

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(newList: List<ResultContest>){
        oldList = list
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ContestItemCallback(newList, oldList)
        )
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var event = itemView.findViewById<TextView>(R.id.ongoing_contestName)!!
        var logo = itemView.findViewById<ImageView>(R.id.ongoing_contestlogo)!!
        var timer = itemView.findViewById<TextView>(R.id.ongoing_contestTime)!!
        var c: CountDownTimer = object :CountDownTimer(0 ,10){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
            }

        }


    }

    class ContestItemCallback(
        private var newList: List<ResultContest>,
        private var oldList: List<ResultContest>
    ) : DiffUtil.Callback(){
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
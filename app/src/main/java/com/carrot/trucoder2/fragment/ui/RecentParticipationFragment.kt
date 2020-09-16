package com.carrot.trucoder2.fragment.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carrot.trucoder2.R
import com.carrot.trucoder2.adapter.RecentParticipationAdapter
import com.carrot.trucoder2.model.RecentParticipation
import kotlinx.android.synthetic.main.fragment_recent_participation.*

class RecentParticipationFragment() : Fragment(R.layout.fragment_recent_participation) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = arguments?.getParcelableArrayList<RecentParticipation>("list")!!
        val recentParticipationAdapter = RecentParticipationAdapter()
        recentParticipationAdapter.submitList(list)
        fragment_recentparticipation_recycle.apply {
            this.adapter = recentParticipationAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }



    }
}
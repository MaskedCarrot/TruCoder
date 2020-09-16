package com.carrot.trucoder2.fragment.contests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.carrot.trucoder2.R
import com.carrot.trucoder2.activity.MainActivity
import com.carrot.trucoder2.adapter.FragmentSubContestRecycle
import com.carrot.trucoder2.adapter.OngoingContestAdapter
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_sub_contests0.*

class SubContestsFragment2 : Fragment(R.layout.fragment_sub_contests0) {

    private lateinit var viewModel : MainActivityViewModel
    private lateinit var adapter : FragmentSubContestRecycle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleview()
        viewModel = (activity as MainActivity).viewModel
        viewModel.getCodeforcesContest().observe(viewLifecycleOwner , Observer {
            adapter.submitList(it)
        })
        val viewpager2Apdapter = OngoingContestAdapter()
        fragsubcontests0_viewpager.adapter  = viewpager2Apdapter
        fragsubcontests0_viewpager.layoutManager = LinearLayoutManager(activity , RecyclerView.HORIZONTAL, false)
        viewModel.getAllRunningCFContests().observe(viewLifecycleOwner , Observer {
            viewpager2Apdapter.submitList(it)
        })

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(fragsubcontests0_viewpager)

    }

    private fun setUpRecycleview(){
        adapter = FragmentSubContestRecycle(listener = {
            val contestBottomSheet = ContestViewBottomSheet(it)
            contestBottomSheet.show(childFragmentManager , contestBottomSheet.tag)
        })
        fragsubcontests0_recycleview.adapter = adapter
        fragsubcontests0_recycleview.layoutManager = LinearLayoutManager(activity)
    }
}
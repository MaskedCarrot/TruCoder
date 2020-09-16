package com.carrot.trucoder2.fragment.contests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.carrot.trucoder2.utils.Resource
import com.carrot.trucoder2.R
import com.carrot.trucoder2.activity.MainActivity
import com.carrot.trucoder2.adapter.FragmentContestViewpagerAdapter
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_contests.*


class ContestsFragment : Fragment(R.layout.fragment_contests) {

    private lateinit var viewModel : MainActivityViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val viewpagerAdapter = FragmentContestViewpagerAdapter(activity as MainActivity)
        fragcontests_veiwpager.adapter = viewpagerAdapter




        TabLayoutMediator(fragcontests_tablayout, fragcontests_veiwpager) { tab, position ->
            when(position){
                0->tab.text = "All"
                1->tab.text = "Codechef"
                2->tab.text = "Codeforces"
                3->tab.text = "Google"
                4->tab.text = "TopCoder"
            }
        }.attach()

    }

}
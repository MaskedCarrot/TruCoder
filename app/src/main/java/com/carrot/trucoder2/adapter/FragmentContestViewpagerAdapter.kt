package com.carrot.trucoder2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.carrot.trucoder2.fragment.contests.*


class FragmentContestViewpagerAdapter  (fa : FragmentActivity) : FragmentStateAdapter(fa){
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1-> SubContestsFragment1()
            2-> SubContestsFragment2()
            3-> SubContestsFragment6()
            4-> SubContestsFragment3()
            5-> SubContestsFragment4()
            else-> SubContestsFragment0()
        }
    }
}
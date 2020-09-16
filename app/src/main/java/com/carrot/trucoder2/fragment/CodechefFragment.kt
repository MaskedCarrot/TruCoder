package com.carrot.trucoder2.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.carrot.trucoder2.R
import com.carrot.trucoder2.activity.MainActivity
import com.carrot.trucoder2.model.RecentParticipation
import com.carrot.trucoder2.model.ResponseCodechef
import com.carrot.trucoder2.utils.Functions
import com.carrot.trucoder2.utils.Resource
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_codechef.*


class CodechefFragment : Fragment(R.layout.fragment_codechef) {

    private lateinit var viewModel : MainActivityViewModel
    private var flag  = 1
    private var list :List<RecentParticipation> = ArrayList()
    var handle = "=_="


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel


        val sharedPref = (activity as MainActivity).getSharedPreferences("secret" , Context.MODE_PRIVATE)
        val cchandle = sharedPref.getString("CCH" ,"=_=")!!


        viewModel.codechefUserLD.observe(viewLifecycleOwner , {
            when(it){
                is Resource.Success ->{
                    it.data?.let { it1 -> setTextView(it1)
                        flag = 2
                        codechef_errorState.visibility =View.GONE
                        mainview.visibility = View.VISIBLE
                        anim_1.visibility = View.GONE
                        list = it.data.resultCodechefContest_ratings
                        viewModel.RefreshCCFriends(cchandle)
                    }
                }
                is Resource.Error->{
                    anim_1.visibility = View.GONE
                    codechef_errorState.visibility = View.VISIBLE
                }
                is Resource.Loading->{
                    mainview.visibility = View.GONE
                    anim_1.visibility = View.VISIBLE
                    codechef_errorState.visibility = View.GONE
                }
            }

        })


        codechef_errorState.setOnClickListener{
            mainview.visibility = View.GONE
            anim_1.visibility = View.VISIBLE
            codechef_errorState.visibility = View.GONE
            viewModel.getCodeChefUser(cchandle)
        }
        fragcodechefcontent_leaderboadbtn.setOnClickListener{
            viewModel.getAllCCFriends().observe(viewLifecycleOwner , {
                val bundle = Bundle()
                bundle.putInt("ID" , 2)
                bundle.putParcelableArrayList("list1" , it as ArrayList<out Parcelable>?)
                findNavController().navigate(R.id.action_nav_codechef_to_FriendsFragment , bundle)
            })
        }

       fragcodechefcontent_recentparticipation.setOnClickListener{
            if (flag == 2) {
                val bundle = Bundle()
                println(list)
                bundle.putParcelableArrayList("list" , list as ArrayList<out Parcelable>?)
                findNavController().navigate(R.id.action_nav_codechef_to_RecentParticipationFragment , bundle)
            }
        }



        rr1.setOnClickListener{
            if(switch1.visibility == View.VISIBLE) {
                switch1.visibility = View.INVISIBLE
                ll1.visibility = View.VISIBLE
                cc_line1.visibility = View.VISIBLE
            }
            else{
                switch1.visibility = View.VISIBLE
                ll1.visibility = View.GONE
                cc_line1.visibility= View.INVISIBLE
            }
        }

        rr2.setOnClickListener{
            if(switch2.visibility == View.VISIBLE) {
                switch2.visibility = View.INVISIBLE
                ll2.visibility = View.VISIBLE
                cc_line2.visibility = View.VISIBLE
            }
            else{
                switch2.visibility = View.VISIBLE
                ll2.visibility = View.GONE
                cc_line2.visibility = View.INVISIBLE
            }
        }

        rr3.setOnClickListener{
            if(switch3.visibility == View.VISIBLE) {
                switch3.visibility = View.INVISIBLE
                ll3.visibility = View.VISIBLE
                cc_line3.visibility = View.VISIBLE
            }
            else{
                switch3.visibility = View.VISIBLE
                ll3.visibility = View.GONE
                cc_line3.visibility = View.INVISIBLE
            }
        }

        rr4.setOnClickListener{
            if(switch4.visibility == View.VISIBLE) {
                switch4.visibility = View.INVISIBLE
                ll4.visibility = View.VISIBLE
                cc_line4.visibility = View.VISIBLE
            }
            else{
                switch4.visibility = View.VISIBLE
                ll4.visibility = View.GONE
                cc_line4.visibility = View.INVISIBLE
            }
        }




    }

    private fun setTextView(responseCodechef: ResponseCodechef) {
        fragcodechefcontent_overallcrank.text = responseCodechef.global_rank.toString()
        fragcodechefcontent_overallgrank.text = responseCodechef.country_rank.toString()
        fragcodechefcontent_lunchcrank.text = responseCodechef.resultCodechefContests[2].country_rank.toString()
        fragcodechefcontent_lunchgrank.text = responseCodechef.resultCodechefContests[2].global_rank.toString()
        fragcodechefcontent_currentRating.text = responseCodechef.rating.toString()
        fragcodechefcontent_lunchRating.text = responseCodechef.resultCodechefContests[2].rating.toString()
        fragcodechefcontent_longRating.text = responseCodechef.resultCodechefContests[0].rating.toString()
        fragcodechefcontent_longcrank.text = responseCodechef.resultCodechefContests[0].country_rank.toString()
        fragcodechefcontent_longgrank.text = responseCodechef.resultCodechefContests[0].global_rank.toString()
        fragcodechefcontent_cookRating.text = responseCodechef.resultCodechefContests[1].rating.toString()
        fragcodechefcontent_cookcrank.text = responseCodechef.resultCodechefContests[1].country_rank.toString()
        fragcodechefcontent_cookgrank.text = responseCodechef.resultCodechefContests[1].global_rank.toString()
        setGraph(responseCodechef.resultCodechefContest_ratings)
        val star = Functions.getCodeforcesStars(responseCodechef.stars[0].toInt())
        if(star != -1) fragcodechefcontent_stars.setImageResource(star)

    }

    private fun setGraph(resultCodechefContestRating: List<RecentParticipation>){
        val list = mutableListOf<Entry>()
        for (i in resultCodechefContestRating.indices){
            list.add(Entry( i.toFloat() ,resultCodechefContestRating[i].rating.toFloat() , resultCodechefContestRating[i].event))
        }
        val dataSet = LineDataSet(list , "Rating")
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.setDrawFilled(true)
        dataSet.setDrawValues(false)
        dataSet.color = Color.parseColor("#1a73e8")
        dataSet.fillColor =Color.parseColor("#0049b5")
        val lineData = LineData(dataSet)
        fragcodechefcontent_chart.isDoubleTapToZoomEnabled = false
        fragcodechefcontent_chart.data = lineData
        fragcodechefcontent_chart.setPinchZoom(false)
        fragcodechefcontent_chart.xAxis.setDrawGridLines(false)
        fragcodechefcontent_chart.setDrawBorders(false)
    }

}

package com.carrot.trucoder2.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.carrot.trucoder2.R
import com.carrot.trucoder2.activity.MainActivity
import com.carrot.trucoder2.model.Leaderboard
import com.carrot.trucoder2.model.RecentParticipation
import com.carrot.trucoder2.model.ResponseCodforces
import com.carrot.trucoder2.utils.Functions
import com.carrot.trucoder2.utils.Functions.Companion.ConvertDateTimeFomMill
import com.carrot.trucoder2.utils.Resource
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_codeforces.*

class CodeforcesFragment : Fragment(R.layout.fragment_codeforces) {

    private lateinit var viewModel : MainActivityViewModel
    var list :List<RecentParticipation> = ArrayList()
    var list1:List<Leaderboard> = ArrayList()
    var flag = 1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val sharedPref = (activity as MainActivity).getSharedPreferences("secret" , Context.MODE_PRIVATE)
        val cfhandle = sharedPref.getString("CFH" ,"=_=")!!



        viewModel.codeforcesUserLD.observe(viewLifecycleOwner , Observer {
            when(it){
                is Resource.Success->{
                    it.data?.let { it1 -> setText(it1)
                        flag = 2
                        codeforces_errorState.visibility = View.GONE
                        anim_2.visibility = View.GONE
                        mainview2.visibility = View.VISIBLE
                        list = it.data.resutsCodeforcesContests
                        viewModel.RefreshCFFriends(cfhandle)
                    }
                }
                is Resource.Error->{
                    codeforces_errorState.visibility = View.VISIBLE
                    anim_2.visibility = View.GONE
                    mainview2.visibility = View.GONE
                }
                is Resource.Loading->{
                    codeforces_errorState.visibility = View.GONE
                    anim_2.visibility = View.VISIBLE
                    mainview2.visibility = View.GONE
                }
            }
        })

        codeforces_errorState.setOnClickListener {
            codeforces_errorState.visibility = View.GONE
            anim_2.visibility = View.VISIBLE
            mainview2.visibility = View.GONE
            viewModel.getCodeforcesUser(cfhandle)
        }


        ll6.setOnClickListener{
            if(ll7.visibility != View.VISIBLE)
                ll7.visibility = View.VISIBLE
            else
                ll7.visibility = View.GONE
        }


        fragcodeforcescontent_recentparticipation.setOnClickListener(View.OnClickListener {
            if (flag == 2) {
                val bundle = Bundle()
                bundle.putParcelableArrayList("list" , list as ArrayList<out Parcelable>?)
                findNavController().navigate(R.id.action_nav_codeforces_to_RecentParticipationFragment , bundle)
            }
            })
        fragcodeforcescontent_leaderboadbtn.setOnClickListener {
            viewModel.getAllCFFriends().observe(viewLifecycleOwner , Observer {
                val bundle = Bundle()
                bundle.putInt("ID" , 1)
                bundle.putParcelableArrayList("list1" , it as java.util.ArrayList<out Parcelable>)
                println(list1)
                findNavController().navigate(R.id.action_nav_codeforces_to_FriendsFragment, bundle)
            })

        }


    }


    private fun setText(responseCodforces: ResponseCodforces){
        fragcodeforcescontent_highestRank.text = responseCodforces.max_rank.toString()
        fragcodeforcescontent_currentRank.text = responseCodforces.rank
        fragcodeforcescontent_highestRating.text = responseCodforces.max_rating.toString()
        fragcodeforcescontent_currentRating.text = responseCodforces.rating.toString()
        fragcodeforcescontent_lastseen.text = ConvertDateTimeFomMill(responseCodforces.lastSeen.toLong())
        Glide.with(this).load(responseCodforces.photo.toString()).into(fragcodeforces_headerimage)
        setGraph(responseCodforces.resutsCodeforcesContests)
        val c = Functions.getCodeforcesColor(responseCodforces.rating)
        fragcodeforcescontent_currentRank.setTextColor(resources.getColor(c))
        fragcodeforcescontent_currentRating.setTextColor(resources.getColor(c))


    }

    private fun setGraph(resutsCodeforcesContests: List<RecentParticipation>){
        val list = mutableListOf<Entry>()
        for (i in 0..resutsCodeforcesContests.size-1){
            list.add(Entry( i.toFloat() , resutsCodeforcesContests[i].rating.toFloat()))
        }
        val dataSet = LineDataSet(list , "Rating")
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.setDrawFilled(true);
        dataSet.setDrawValues(false);
        dataSet.color = Color.parseColor("#1a73e8")
        dataSet.fillColor =Color.parseColor("#0049b5")
        val lineData :LineData = LineData(dataSet)
        fragcodeforcescontent_chart.isDoubleTapToZoomEnabled = false
        fragcodeforcescontent_chart.setPinchZoom(false)
        fragcodeforcescontent_chart.data = lineData
        fragcodeforcescontent_chart.getXAxis().setDrawGridLines(false);
        fragcodeforcescontent_chart.getXAxis().setDrawAxisLine(false)
        fragcodeforcescontent_chart.getAxisLeft().setDrawAxisLine(false)
        fragcodeforcescontent_chart.getAxisLeft().setDrawGridLines(false)
        fragcodeforcescontent_chart.setDrawBorders(false)
    }

}
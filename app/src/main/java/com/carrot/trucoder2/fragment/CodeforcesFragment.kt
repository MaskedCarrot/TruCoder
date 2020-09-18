
package com.carrot.trucoder2.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.carrot.trucoder2.R
import com.carrot.trucoder2.activity.MainActivity
import com.carrot.trucoder2.model.Leaderboard
import com.carrot.trucoder2.model.RecentParticipation
import com.carrot.trucoder2.model.ResponseCodforces
import com.carrot.trucoder2.utils.Constants
import com.carrot.trucoder2.utils.Functions
import com.carrot.trucoder2.utils.Functions.Companion.ConvertDateTimeFomMill
import com.carrot.trucoder2.utils.NameDialog
import com.carrot.trucoder2.utils.Resource
import com.carrot.trucoder2.viewmodel.DetailsViewModel
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.style.FoldingCube
import com.github.ybq.android.spinkit.style.RotatingCircle
import kotlinx.android.synthetic.main.fragment_codechef.*
import kotlinx.android.synthetic.main.fragment_codeforces.*
import java.net.URL

class CodeforcesFragment : Fragment(R.layout.fragment_codeforces) , NameDialog.NameDialogListener {

    private lateinit var viewModel : MainActivityViewModel
    private val viewmodel2 : DetailsViewModel by viewModels()
    var list :List<RecentParticipation> = ArrayList()
    private var list1:List<Leaderboard> = ArrayList()
    private var flag = 1
    var handle = "=_="



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val sharedPref = (activity as MainActivity).getSharedPreferences("secret" , Context.MODE_PRIVATE)
        handle = sharedPref.getString("CFH" ,"=_=")!!
        anim_2.setIndeterminateDrawable(DoubleBounce())


        if(handle == "=_="){
            anim_2.visibility = View.GONE
            setCodeforcesUserName.visibility = View.VISIBLE
        }

        setCodeforcesUserName.setOnClickListener {
            noNameProtocol()
        }

        viewModel.codeforcesUserLD.observe(viewLifecycleOwner , {
            when(it){
                is Resource.Success->{
                    it.data?.let { it1 ->
                        try{
                            setText(it1)
                            flag = 2
                            codeforces_errorState.visibility = View.GONE
                            anim_2.visibility = View.GONE
                            mainview2.visibility = View.VISIBLE
                            list = it1.resutsCodeforcesContests
                        }catch(e:Exception){
                            android.widget.Toast.makeText(context , "There is not enough data for $handle" , android.widget.Toast.LENGTH_LONG).show()
                            codeforces_errorState.visibility = View.VISIBLE
                            anim_2.visibility = View.GONE
                            mainview2.visibility = View.GONE
                        }
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
            viewModel.getCodeforcesUser(handle)
        }

        ll6.setOnClickListener{
            if(ll7.visibility != View.VISIBLE)
                ll7.visibility = View.VISIBLE
            else
                ll7.visibility = View.GONE
        }

        fragcodeforcescontent_recentparticipation.setOnClickListener{
            if (flag == 2) {
                val bundle = Bundle()
                bundle.putParcelableArrayList("list" , list as ArrayList<out Parcelable>?)
                findNavController().navigate(R.id.action_nav_codeforces_to_RecentParticipationFragment , bundle)
            }
            }

        fragcodeforcescontent_leaderboadbtn.setOnClickListener {
            viewModel.getAllCFFriends().observe(viewLifecycleOwner ,{
                val bundle = Bundle()
                bundle.putInt("ID" , 1)
                bundle.putParcelableArrayList("list1" , it as java.util.ArrayList<out Parcelable>)
                println(list1)
                findNavController().navigate(R.id.action_nav_codeforces_to_FriendsFragment, bundle)
            })

        }

        viewmodel2.result.observe(viewLifecycleOwner , {
            when (it) {
                1 -> {
                    setCodeforcesUserName.visibility =View.GONE
                    viewModel.getCodeforcesUser(handle)
                    val sharedPreferences = requireContext().getSharedPreferences("secret" , Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("CFH", handle)
                    editor.apply()
                }
                0 -> {
                    Toast.makeText( requireContext(), "$handle does not exists", Toast.LENGTH_LONG).show()
                    handle = "=_="
                }
                -1->{
                    Toast.makeText(requireContext() ,"There was an error while searching for $handle"  , Toast.LENGTH_LONG).show()
                    handle = "=_="
                }
            }
        })
    }


    private fun setText(responseCodforces: ResponseCodforces){
        fragcodeforcescontent_highestRank.text = responseCodforces.max_rank
        fragcodeforcescontent_currentRank.text = responseCodforces.rank
        fragcodeforcescontent_highestRating.text = responseCodforces.max_rating.toString()
        fragcodeforcescontent_currentRating.text = responseCodforces.rating.toString()
        fragcodeforcescontent_lastseen.text = ConvertDateTimeFomMill(responseCodforces.lastSeen.toLong())
        Glide.with(this).load(responseCodforces.photo).into(fragcodeforces_headerimage)
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
        dataSet.setDrawFilled(true)
        dataSet.setDrawValues(false)
        dataSet.color = Color.parseColor("#1a73e8")
        dataSet.fillColor =Color.parseColor("#0049b5")
        val lineData :LineData = LineData(dataSet)
        fragcodeforcescontent_chart.isDoubleTapToZoomEnabled = false
        fragcodeforcescontent_chart.setPinchZoom(false)
        fragcodeforcescontent_chart.data = lineData
        fragcodeforcescontent_chart.getXAxis().setDrawGridLines(false)
        fragcodeforcescontent_chart.getXAxis().setDrawAxisLine(false)
        fragcodeforcescontent_chart.getAxisLeft().setDrawAxisLine(false)
        fragcodeforcescontent_chart.getAxisLeft().setDrawGridLines(false)
        fragcodeforcescontent_chart.setDrawBorders(false)
    }

    private fun noNameProtocol(){
        val dialog = NameDialog()
        dialog.show(childFragmentManager , "Codeforces_NO_NAME")
    }

    override fun applyUsername(handle: String) {
        val url = URL(Constants.CODEFORCES_PROFILE_URL + handle)
        viewmodel2.checkHandle(url)
        this.handle = handle
    }
}
package com.carrot.trucoder2.fragment.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carrot.trucoder2.R
import com.carrot.trucoder2.activity.MainActivity
import com.carrot.trucoder2.adapter.LeaderboardAdapter
import com.carrot.trucoder2.model.Leaderboard
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_friends.*


class FriendsFragment : Fragment(R.layout.fragment_friends){

    private lateinit var viewModel : MainActivityViewModel
    private lateinit var leaderboardAdapter : LeaderboardAdapter
    var cfhandle = ""
    var cchandle = ""
    var list :List<Leaderboard> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val id = arguments?.getInt("ID")!!
        leaderboardAdapter = LeaderboardAdapter(requireContext())


        val sharedPref = activity?.getSharedPreferences("secret", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            cfhandle = sharedPref.getString("CFH", "")!!
            cchandle = sharedPref.getString("CCH", "")!!
        }


        when(id){
            1 -> refreshCF()
            2 -> refreshCC()
        }

        friends_swipe.setOnRefreshListener {
            when(id){
                1 -> refreshCF()
                2 -> refreshCC()
            }
            friends_swipe.isRefreshing = false
        }

        fragmentsubfriends_recycleview.apply {
            this.adapter=  leaderboardAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)  )
        }

        fragmentfriends_floatingbtn.setOnClickListener{
            val bsFriendSearch = BSFriendSearch(id)
            bsFriendSearch.show(childFragmentManager, bsFriendSearch.tag)
        }


        val itemTouchHelperCallback = object :ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(id){
                    1 -> {
                        if (cfhandle != list[viewHolder.adapterPosition].Name) {
                            viewModel.DeleteFriends(list[viewHolder.adapterPosition])
                        }
                        refreshCF()
                    }
                    2 -> {
                        if (cchandle != list[viewHolder.adapterPosition].Name)
                            viewModel.DeleteFriends(list[viewHolder.adapterPosition])
                        refreshCC()
                    }
                }
            }


        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(fragmentsubfriends_recycleview)


    }


    private fun refreshCF(){
        viewModel.RefreshCFFriends(cfhandle)
        viewModel.getAllCFFriends().observe(viewLifecycleOwner, {
            leaderboardAdapter.submitList(it)
            list = it
        })
    }

    private fun refreshCC(){
        viewModel.RefreshCCFriends(cchandle)
        viewModel.getAllCCFriends().observe(viewLifecycleOwner, {
            leaderboardAdapter.submitList(it)
            list = it
        })}

}
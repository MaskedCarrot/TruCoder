package com.carrot.trucoder2.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.carrot.trucoder2.R
import com.carrot.trucoder2.databinding.ActivityMainBinding
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sharedPref = this.getSharedPreferences("secret" , Context.MODE_PRIVATE)
        val cfhandle = sharedPref.getString("CFH", "=_=")!!
        val cchandle = sharedPref.getString("CCH" ,"=_=")!!


        viewModel.getContestData()
        if(!cfhandle.equals("=_="))
            viewModel.getCodeforcesUser(cfhandle)
        if(!cchandle.equals("=_="))
            viewModel.getCodeChefUser(cchandle)


        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            if(destination.id == R.id.FriendsFragment || destination.id == R.id.RecentParticipationFragment){
                navView.visibility = View.GONE
            }else{
                navView.visibility = View.VISIBLE
            }
        }


    }
}
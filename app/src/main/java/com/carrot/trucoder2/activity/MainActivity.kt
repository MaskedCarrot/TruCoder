package com.carrot.trucoder2.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.carrot.trucoder2.R
import com.carrot.trucoder2.database.CodeDatabase
import com.carrot.trucoder2.databinding.ActivityMainBinding
import com.carrot.trucoder2.repository.CodeRespository
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import com.carrot.trucoder2.viewmodel.MainActivityViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception
import java.net.SocketTimeoutException


class MainActivity : AppCompatActivity(){

    lateinit var viewModel:MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sharedPref = this.getSharedPreferences("secret" , Context.MODE_PRIVATE)
        val cfhandle = sharedPref.getString("CFH", "")!!
        val cchandle = sharedPref.getString("CCH" ,"")!!
        val codeRepository = CodeRespository(CodeDatabase(this))


        val viewModelProviderFactory = MainActivityViewModelProviderFactory(codeRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainActivityViewModel::class.java)

        viewModel.getCodeforcesUser(cfhandle)
        viewModel.getContestData()
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
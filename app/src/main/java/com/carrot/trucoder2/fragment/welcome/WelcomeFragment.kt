package com.carrot.trucoder2.fragment.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.carrot.trucoder2.R
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        w_welcome_next.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_codechefHandleFragment)
        }
    }
}
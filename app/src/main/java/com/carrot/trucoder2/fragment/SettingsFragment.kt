    package com.carrot.trucoder2.fragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.carrot.trucoder2.R
import com.carrot.trucoder2.activity.LoginActivity
import com.carrot.trucoder2.activity.MainActivity
import com.carrot.trucoder2.utils.Constants
import com.carrot.trucoder2.viewmodel.DetailsViewModel
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*
import java.lang.Exception
import java.net.URL

    class SettingsFragment : Fragment(R.layout.fragment_settings) {


        var type = -1
        private lateinit var viewModel : MainActivityViewModel
        val viewModel2 : DetailsViewModel by viewModels()
        var handle = "=_="


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            viewModel = (activity as MainActivity).viewModel
        }catch (e:Exception){
            e.printStackTrace()
        }


        val auth  = FirebaseAuth.getInstance()
        val user = auth.currentUser


        if (user != null) {
            user_name.text = user.displayName
            Glide.with(this).load(user.photoUrl).into(user_photo)
        }

        codechef.setOnClickListener {
            if (settings_codechef.visibility == View.VISIBLE) {
                settings_codechef.visibility = View.GONE
                type = -1
            } else {
                settings_codechef.visibility = View.VISIBLE
                type = 2
            }
        }

        codeforces.setOnClickListener{

            if(settings_codeforces.visibility == View.VISIBLE) {
                settings_codeforces.visibility = View.GONE
                type = -1
            }
            else {
                type = 1
                settings_codeforces.visibility = View.VISIBLE
            }
        }

        review.setOnClickListener{
            val manager = ReviewManagerFactory.create(requireContext())
            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { _ ->
                if (request.isSuccessful) {
                    // We got the ReviewInfo object
                    val reviewInfo= request.result
                    val flow = manager.launchReviewFlow(activity as MainActivity, reviewInfo)
                    flow.addOnCompleteListener { _ ->
                        // The flow has finished. The API does not indicate whether the user
                        // reviewed or not, or even whether the review dialog was shown. Thus, no
                        // matter the result, we continue our app flow.
                        Toast.makeText(requireContext() , "Thank you for your time" , Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext() , "Please try again later" , Toast.LENGTH_SHORT).show()
                }
            }


        }

        bug.setOnClickListener{
            val emailIntent = Intent(Intent.ACTION_VIEW , Uri.parse("mailto:" + "2000apoorv@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bug in TruCoder")
            try {
                this.startActivity(
                    Intent.createChooser(
                        emailIntent,
                        "Send email using..."
                    )
                )
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    activity,
                    "No email clients installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        feature.setOnClickListener{
            val emailIntent = Intent(Intent.ACTION_VIEW , Uri.parse("mailto:" + "2000apoorv@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feature Request")
            try {
                this.startActivity(
                    Intent.createChooser(
                        emailIntent,
                        "Send email using..."
                    )
                )
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    activity,
                    "No email clients installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        github.setOnClickListener{
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/MaskedCarrot/TruCoder")
            )
            startActivity(browserIntent)
        }

        signout.setOnClickListener{
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        codechef_getHandle.setOnEditorActionListener { _, i, _ ->
                if(i == EditorInfo.IME_ACTION_NEXT){
                    handle = codechef_getHandle.text.toString()
                    if(handle.isNotEmpty()) {
                        val url = URL(Constants.CODEFORCES_PROFILE_URL + handle)
                        viewModel2.checkHandle(url)
                    }
                    else {
                        Toast.makeText(requireContext() , "Please enter your handle first" , Toast.LENGTH_SHORT).show()
                    }
                    true
                }else false
            }


        codeforces_getHandle.setOnEditorActionListener { _, i, _ ->
                if(i == EditorInfo.IME_ACTION_NEXT){
                    handle = codeforces_getHandle.text.toString()
                    if(handle.isNotEmpty()) {
                        val url = URL(Constants.CODEFORCES_PROFILE_URL + handle)
                        viewModel2.checkHandle(url)
                    }
                    else {
                        Toast.makeText(requireContext() , "Please enter your handle first" , Toast.LENGTH_SHORT).show()
                    }
                    true
                }else false
            }

        viewModel2.result.observe(viewLifecycleOwner, {
            when (it) {
                1 -> {
                    val sharedPreferences = requireContext().getSharedPreferences("secret" , Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    when(id){
                        1->{editor.putString("CCH", handle)
                            codeforcesNameChangeProtocol()
                        }
                        2->{editor.putString("CFH", handle)
                            codechefNameChangeProtocol()
                        }
                    }

                    editor.apply()
                }
                0 -> {
                    Toast.makeText( requireContext(), "$handle does not exists", Toast.LENGTH_LONG).show()
                }
                -1->{
                    Toast.makeText(requireContext() ,"There was an error while searching for $handle"  , Toast.LENGTH_LONG).show()
                }
            }
        })
    }


        private fun codechefNameChangeProtocol(){
            viewModel.afterMathsCodechef(handle)
        }

        private fun codeforcesNameChangeProtocol(){
            viewModel.afterMathsCodeforces(handle)
        }



    }
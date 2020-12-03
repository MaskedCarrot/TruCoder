    package com.carrot.trucoder2.fragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.carrot.trucoder2.R
import com.carrot.trucoder2.activity.AuthActivity
import com.carrot.trucoder2.activity.MainActivity
import com.carrot.trucoder2.utils.Constants
import com.carrot.trucoder2.utils.NameDialog
import com.carrot.trucoder2.viewmodel.DetailsViewModel
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import com.github.ybq.android.spinkit.style.Circle
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*
import java.net.URL

    class SettingsFragment : Fragment(R.layout.fragment_settings) , NameDialog.NameDialogListener {


        private var type = -1
        private lateinit var viewModel : MainActivityViewModel
        private val viewModel2 : DetailsViewModel by viewModels()
        var handle = "=_="


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            viewModel = (activity as MainActivity).viewModel


            val auth  = FirebaseAuth.getInstance()
            val user = auth.currentUser

            settingsspin2.setIndeterminateDrawable(Circle())
            settingsspin1.setIndeterminateDrawable(Circle())


            if (user != null) {
                user_name.text = user.displayName
                Glide.with(this).load(user.photoUrl).into(user_photo)
            }

            codechef.setOnClickListener {
                type = 2
                openNameDialog()
            }

            codeforces.setOnClickListener{
                type = 1
                openNameDialog()
            }

            review.setOnClickListener{
                val manager = ReviewManagerFactory.create(requireContext())
                val request = manager.requestReviewFlow()
                request.addOnCompleteListener { requset ->
                    if (requset.isSuccessful) {
                        val reviewInfo= request.result
                        val flow = manager.launchReviewFlow(requireActivity(), reviewInfo)
                        flow.addOnCompleteListener {
                            Toast.makeText(context , "Thank you for your time" , Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Please try again later" , Toast.LENGTH_SHORT).show()
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
                startActivity(Intent(activity, AuthActivity::class.java))
            }

            viewModel2.result.observe(viewLifecycleOwner, {
                when (it) {
                    1 -> {
                        val sharedPreferences = requireContext().getSharedPreferences("secret" , Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        when(type){
                            1->{editor.putString("CFH", handle)
                                codeforcesNameChangeProtocol()
                            }
                            2->{editor.putString("CCH", handle)
                                codechefNameChangeProtocol()
                            }
                        }

                        editor.apply()
                    }
                    0 -> {
                        settingsspin2.visibility= View.GONE
                        settingsspin1.visibility= View.GONE
                        Toast.makeText( requireContext(), "$handle does not exists", Toast.LENGTH_LONG).show()
                        handle = "=_="
                    }
                    -1->{
                        settingsspin2.visibility= View.GONE
                        settingsspin1.visibility= View.GONE
                        Toast.makeText(requireContext() ,"There was an error while searching for $handle"  , Toast.LENGTH_LONG).show()
                        handle = "=_="
                    }
                }
                type = -1
            })
        }


        private fun openNameDialog(){
            val dialog = NameDialog()
            dialog.show(childFragmentManager , "Name change Dialog")
        }

        override fun applyUsername(handle: String) {
            if (handle.isBlank()){
                Toast.makeText(context, "Handle cannot be blank", Toast.LENGTH_SHORT).show()
                return
            }
            when(type){
                1->{
                    settingsspin2.visibility= View.VISIBLE
                    this.handle = handle
                    val url = URL(Constants.CODEFORCES_PROFILE_URL + handle)
                    viewModel2.checkHandle(url)
                }
                2->{
                    settingsspin1.visibility = View.VISIBLE
                    this.handle = handle
                    val url = URL(Constants.CODECHEF_PROFILE_URL + handle)
                    viewModel2.checkHandle(url)
                }
            }
        }

        private fun codechefNameChangeProtocol(){
            viewModel.afterMathsCodechef()
            viewModel.getCodeChefUser(handle)
            Toast.makeText(context , "Handle Changed to $handle" , Toast.LENGTH_SHORT).show()
            settingsspin1.visibility = View.GONE
        }

        private fun codeforcesNameChangeProtocol(){
            viewModel.afterMathsCodeforces()
            viewModel.getCodeforcesUser(handle)
            Toast.makeText(context , "Handle Changed to $handle" , Toast.LENGTH_SHORT).show()
            settingsspin2.visibility = View.GONE
        }




    }
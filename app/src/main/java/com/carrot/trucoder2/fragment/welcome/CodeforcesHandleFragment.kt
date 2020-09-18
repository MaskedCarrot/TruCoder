package com.carrot.trucoder2.fragment.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.carrot.trucoder2.R
import com.carrot.trucoder2.activity.MainActivity
import com.carrot.trucoder2.utils.Constants.CODEFORCES_PROFILE_URL
import com.carrot.trucoder2.utils.Functions.Companion.hideSoftKeyboard
import com.carrot.trucoder2.viewmodel.DetailsViewModel
import com.github.ybq.android.spinkit.style.Circle
import com.github.ybq.android.spinkit.style.FoldingCube
import kotlinx.android.synthetic.main.fragment_codeforces_handle.*
import java.net.URL

class CodeforcesHandleFragment : Fragment(R.layout.fragment_codeforces_handle) {

    val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var handle = ""
        CodeforcesHandlespin_kit.setIndeterminateDrawable(Circle())


        w_codeforces_getHandle.setOnEditorActionListener { _, i, _ ->
            hideSoftKeyboard(requireActivity())
            CodeforcesHandlespin_kit.visibility = View.VISIBLE
            if(i == EditorInfo.IME_ACTION_NEXT){
                handle = w_codeforces_getHandle.text.toString()
                if(handle.isNotEmpty()) {
                    val url = URL(CODEFORCES_PROFILE_URL + handle)
                    viewModel.checkHandle(url)
                }
                else {
                    handle = ""
                    Toast.makeText(requireContext() , "Please enter your handle first" , Toast.LENGTH_SHORT).show()
                }
                true
            }else false
        }

        viewModel.result.observe(viewLifecycleOwner, {
            when (it) {
                1 -> {
                    val sharedPreferences = requireContext().getSharedPreferences("secret" , Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("CFH", handle)
                    editor.apply()
                    CodeforcesHandlespin_kit.visibility = View.GONE
                    startActivity(Intent(context, MainActivity::class.java))
                    requireActivity().finish()
                }
                0 -> {
                    w_codeforces_getHandle.setText("")
                    CodeforcesHandlespin_kit.visibility = View.GONE
                    Toast.makeText( requireContext(), "$handle does not exists", Toast.LENGTH_LONG).show()
                }
                -1->{
                    CodeforcesHandlespin_kit.visibility = View.GONE
                    Toast.makeText(requireContext() ,"There was an error while searching for $handle"  , Toast.LENGTH_LONG).show()
                }
            }
        })


        w_codeforces_skip.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("secret" , Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("CFH" , "=_=")
            editor.apply()
            startActivity(Intent(context, MainActivity::class.java))
            requireActivity().finish()
        }
    }
}
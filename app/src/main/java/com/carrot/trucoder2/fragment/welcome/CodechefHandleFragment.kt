package com.carrot.trucoder2.fragment.welcome

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.carrot.trucoder2.R
import com.carrot.trucoder2.utils.Constants.CODECHEF_PROFILE_URL
import com.carrot.trucoder2.utils.Functions.Companion.hideSoftKeyboard
import com.carrot.trucoder2.viewmodel.DetailsViewModel
import com.github.ybq.android.spinkit.style.Circle
import com.github.ybq.android.spinkit.style.FoldingCube
import kotlinx.android.synthetic.main.fragment_codechef_handle.*
import java.net.URL


class CodechefHandleFragment : Fragment(R.layout.fragment_codechef_handle) {


    private val viewModel: DetailsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var handle = ""

        CodechefHandlespin_kit.setIndeterminateDrawable(Circle())


        w_codechef_getHandle.setOnEditorActionListener{ _, i, _ ->
            hideSoftKeyboard(requireActivity())
            CodechefHandlespin_kit.visibility = View.VISIBLE
            if(i == EditorInfo.IME_ACTION_NEXT){
                handle = w_codechef_getHandle.text.toString()
                if(handle.isNotEmpty()) {
                    val url = URL(CODECHEF_PROFILE_URL + handle)
                    viewModel.checkHandle(url)
                }
                else {
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
                    editor.putString("CCH", handle)
                    editor.apply()
                    CodechefHandlespin_kit.visibility = View.GONE
                    findNavController().navigate(R.id.action_codechefHandleFragment_to_codeforcesHandleFragment)
                }
                0 -> {
                    w_codechef_getHandle.setText("")
                    CodechefHandlespin_kit.visibility = View.GONE
                    Toast.makeText( requireContext(), "$handle does not exists", Toast.LENGTH_LONG).show()
                }
                -1->{
                    CodechefHandlespin_kit.visibility = View.GONE
                    Toast.makeText(requireContext() ,"There was an error while searching for $handle"  , Toast.LENGTH_LONG).show()
                }
            }
        })


        w_codechef_skip.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("secret", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("CCH", "=_=")
            editor.apply()
            findNavController().navigate(R.id.action_codechefHandleFragment_to_codeforcesHandleFragment)
        }
    }
}
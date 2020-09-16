package com.carrot.trucoder2.fragment.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.carrot.trucoder2.R
import com.carrot.trucoder2.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_time_zome.*
import java.util.*
import kotlin.collections.ArrayList


class TimeZomeFragment : Fragment(R.layout.fragment_time_zome), AdapterView.OnItemSelectedListener {

    private lateinit var ids:Array<String>
    private val zones = ArrayList<TimeZone>()
    private val country = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ids = TimeZone.getAvailableIDs()
        var i = 0
        for (id in ids) {
            if(i > 0 && country[i-1] == TimeZone.getTimeZone(id).displayName )
                continue
            country.add(TimeZone.getTimeZone(id).displayName)
            zones.add(TimeZone.getTimeZone(id))
            i++
        }


        val adapter:ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            country
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        w_timezone_spinner.adapter = adapter
        w_timezone_spinner.onItemSelectedListener = this
        w_timezone_spinner.setSelection(200)

        w_letsGO.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val sharedPreferences = requireContext().getSharedPreferences("secret" , Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("TIME", zones[p2].rawOffset)
        editor.apply()
        Toast.makeText(requireContext() , zones[p2].displayName , Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}
package com.carrot.trucoder2.utils

import android.app.Activity
import android.content.Context
import android.text.format.DateFormat
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.carrot.trucoder2.R

class Functions {
    companion object{
        const val BASE_URL = "http://code-carrot.herokuapp.com"
        @JvmStatic
        fun hideSoftKeyboard(activity: Activity) {
            val view = activity.currentFocus
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }

        @JvmStatic
        fun showSoftKeyboard(view: View , activity: Activity) {

            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, 0)
        }

        fun getCodeforcesStars(stars: Int):Int{
            when(stars-48){
                1 -> return R.drawable.astar
                2 -> return R.drawable.bstar
                3 -> return R.drawable.cstar
                4 -> return R.drawable.dstar
                5 -> return R.drawable.estar
                6 -> return R.drawable.fstar
                7 -> return R.drawable.gstar
                else->return -1
            }
        }

        fun getCodeforcesColor(rating: Int):Int{
            var c = 0
            if (rating < 1200) {
                c = R.color.newbie
            }else if (rating < 1400) {
                c =  R.color.pupil
            } else if (rating < 1600) {
                c = R.color.specilist
            } else if (rating < 1900){
                c =  R.color.expert
            }else if (rating < 2200) {
                c=  R.color.CandidateMaster
            }else if (rating < 2400) {
                    c = R.color.InternationalMaster
            }else if (rating < 2900) {
                c =  R.color.InternationalGrandmaster
            }else{
                c =R.color.LegendaryGrandmaster

            }
            return c
        }

        fun ConvertDateFromMill(mill :Long):String{
            val date = DateFormat.format("dd-MMM-yyyy", mill*1000).toString()
            return date
        }

        fun ConvertTimeFomMill(mill:Long):String{
            val time = DateFormat.format("hh:mm a", mill*1000).toString()
            return time
        }

        fun ConvertDateTimeFomMill(mill:Long):String{
            val time = DateFormat.format("dd-MMM-yyyy hh:mm a", mill*10).toString()
            return time
        }




    }
}
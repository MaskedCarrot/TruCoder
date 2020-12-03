package com.carrot.trucoder2.utils

import android.app.Activity
import android.content.Context
import android.text.format.DateFormat
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.carrot.trucoder2.R

class Functions {
    companion object{
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
            return when(stars-48){
                1 -> R.drawable.astar
                2 -> R.drawable.bstar
                3 -> R.drawable.cstar
                4 -> R.drawable.dstar
                5 -> R.drawable.estar
                6 -> R.drawable.fstar
                7 -> R.drawable.gstar
                else-> -1
            }
        }

        fun getCodeforcesColor(rating: Int):Int{
            var c: Int
            when {
                rating < 1200 -> {
                    c = R.color.newbie
                }
                rating < 1400 -> {
                    c =  R.color.pupil
                }
                rating < 1600 -> {
                    c = R.color.specilist
                }
                rating < 1900 -> {
                    c =  R.color.expert
                }
                rating < 2200 -> {
                    c=  R.color.CandidateMaster
                }
                rating < 2400 -> {
                    c = R.color.InternationalMaster
                }
                rating < 2900 -> {
                    c =  R.color.InternationalGrandmaster
                }
                else -> {
                    c =R.color.LegendaryGrandmaster

                }
            }
            return c
        }

        fun ConvertDateFromMill(mill :Long):String{
            return DateFormat.format("dd-MMM-yyyy", mill*1000).toString()
        }

        fun ConvertTimeFomMill(mill:Long):String{
            val time = DateFormat.format("hh:mm a", mill*1000).toString()
            return time
        }

        fun ConvertDateTimeFomMill(mill:Long):String{
            return DateFormat.format("dd-MMM-yyyy hh:mm a", mill*10).toString()
        }




    }
}
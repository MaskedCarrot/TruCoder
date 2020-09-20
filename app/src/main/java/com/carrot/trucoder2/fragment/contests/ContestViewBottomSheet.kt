package com.carrot.trucoder2.fragment.contests

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.carrot.trucoder2.R
import com.carrot.trucoder2.model.ResultContest
import com.carrot.trucoder2.utils.Functions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ContestViewBottomSheet(var contest: ResultContest): BottomSheetDialogFragment() {
    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.bs_contest, null)
        val startDate = contentView.findViewById<TextView>(R.id.contestBottomSheet_startDate)
        val startTime = contentView.findViewById<TextView>(R.id.contestBottomSheet_startTime)
        val endDate = contentView.findViewById<TextView>(R.id.contestBottomSheet_endDate)
        val endTime = contentView.findViewById<TextView>(R.id.contestBottomSheet_endTime)
        val duration = contentView.findViewById<TextView>(R.id.contestBottomSheet_duration)
        val name = contentView.findViewById<TextView>(R.id.contestBottomSheet_name)
        val logo = contentView.findViewById<ImageView>(R.id.contestBottomSheet_logo)
        val calbtn = contentView.findViewById<ImageButton>(R.id.contestBottomSheet_remind)
        val StartDate = Functions.ConvertDateFromMill(contest.timestamp)
        val StartTime = Functions.ConvertTimeFomMill(contest.timestamp)
        val EndDate = Functions.ConvertDateFromMill(contest.endstamp)
        val EndTime = Functions.ConvertTimeFomMill(contest.endstamp)
        startDate.text = StartDate
        startTime.text = StartTime
        endDate.text = EndDate
        endTime.text = EndTime
        duration.text = contest.duration
        name.text = contest.event
        when(contest.id){
            1 -> logo.setImageResource(R.drawable.codeforces)
            2 -> logo.setImageResource(R.drawable.codechef)
            12 -> logo.setImageResource(R.drawable.topcoder)
            35 -> logo.setImageResource(R.drawable.google)
            93 -> logo.setImageResource(R.drawable.atcoder)
        }

        calbtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra("beginTime", contest.timestamp*1000)
            intent.putExtra("allDay", false)
            intent.putExtra("endTime", contest.endstamp*1000)
            intent.putExtra("title", contest.event)
            intent.putExtra("rrule", "FREQ=NO")
            startActivity(intent)
        })
        dialog.setContentView(contentView)
    }
}
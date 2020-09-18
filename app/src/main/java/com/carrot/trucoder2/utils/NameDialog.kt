package com.carrot.trucoder2.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import com.carrot.trucoder2.R
import com.google.android.material.textfield.TextInputEditText
import timber.log.Timber
import java.lang.Exception

class NameDialog : AppCompatDialogFragment() {

    private  lateinit var listener: NameDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            listener = parentFragment as NameDialogListener
        }catch (e:Exception){
            Timber.d("Must implement NameDialog Listener")
            e.printStackTrace()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder : AlertDialog.Builder  = AlertDialog.Builder(activity)
        val layoutInflater = requireActivity().layoutInflater
        val view =  layoutInflater.inflate(R.layout.name_dialog_box , null)

        val editText = view.findViewById<TextInputEditText>(R.id.Dialog_textField)

        builder.setView(view)
            .setTitle("Change Handle")
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Confirm") { _, _ ->
                val handle = editText.text.toString()
                listener.applyUsername(handle)
            }

        return builder.create()

    }

    interface NameDialogListener{
        fun applyUsername(handle :String)
    }
}
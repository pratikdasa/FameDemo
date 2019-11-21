package com.fame.utils

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import android.app.TimePickerDialog
import android.app.DatePickerDialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.fame.R
import com.fame.listeners.DateTimeListeners
import java.util.*

object PopupUtils {

    fun getAlertMessage(context: Context, message: String) {
        val builder: AlertDialog.Builder? = context.let {
            AlertDialog.Builder(it)
        }
        builder.apply {
            this!!.setPositiveButton(
                "OK",
                { dialog, id ->
                })
        }
        builder?.setTitle(context.getString(R.string.app_name))
        builder?.setMessage(message)
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

    fun getConfirmationDialog(context: Context, message: String,positiveClick :View.OnClickListener,negativeClick :View.OnClickListener) {
        val builder: AlertDialog.Builder? = context.let {
            AlertDialog.Builder(it)
        }
        builder.apply {
            this!!.setPositiveButton(
                "Yes",
                { dialog, id ->
                    positiveClick.onClick(null)
                })
            this!!.setNegativeButton(
                "No",
                { dialog, id ->
                    negativeClick.onClick(null)
                })
        }
        builder?.setTitle(context.getString(R.string.app_name))
        builder?.setMessage(message)
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }


    private var datePickerDialog: DatePickerDialog? = null
    fun showDatePicker(context: Context, txtLeaveDate: TextView,dateTimeListeners: DateTimeListeners) {
        val getText = txtLeaveDate.text.toString().trim { it <= ' ' }
        val c = Calendar.getInstance()
        var mYear = c.get(Calendar.YEAR)
        var mMonth = c.get(Calendar.MONTH)
        var mDay = c.get(Calendar.DAY_OF_MONTH)
        if (TextUtils.isEmpty(getText)) {
        } else if (getText.equals("Select Date")) {
         }
        else {
            val dateAndTimes =
                getText.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val dateStr = dateAndTimes[0]
            val dates = dateStr.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            mDay = Integer.parseInt(dates[0])
            mMonth = Integer.parseInt(dates[1])
            mYear = Integer.parseInt(dates[2])
        }
        var dateValue = ""
        datePickerDialog = DatePickerDialog(
            context,
            { view, year, monthOfYear, dayOfMonth ->
                val month = monthOfYear + 1
                dateValue = view.dayOfMonth.toString() + "/" + month + "/" + view.year
                dateTimeListeners.dataTimeListners(dateValue)
            }, mYear, mMonth, mDay
        )
        datePickerDialog!!.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog!!.show()
    }

    fun showTimePicker(context: Context, textView: TextView,dateTimeListeners: DateTimeListeners) {
        var dateAndTime: String = ""
        val c = Calendar.getInstance()
        val mHour = c.get(Calendar.HOUR_OF_DAY)
        val mMinute = c.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
            context,
            { view,
              hourOfDay,
              minute ->
                dateAndTime = " $hourOfDay:$minute"
                dateTimeListeners.dataTimeListners(dateAndTime)
                textView.text = dateAndTime
            },
            mHour,
            mMinute,
            false
        )

        timePickerDialog.show()
        timePickerDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE, "Cancel"
        ) { view, which -> }
        timePickerDialog.setButton(
            DialogInterface.BUTTON_POSITIVE, "Ok"
        ) { view, which -> }
    }

}
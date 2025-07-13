package com.satya.smartmealplanner.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

object UIHelpers {
    fun customToast(context: Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}

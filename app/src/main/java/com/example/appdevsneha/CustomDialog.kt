package com.example.appdevsneha

import android.app.Dialog
import android.content.Context
import android.view.Window

class CustomDialog(context: Context, layoutResId: Int) : Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layoutResId)
    }
}

package com.core.base.utils


import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ToolbarHelper {


    fun setToolbarInFragment(toolbar: Toolbar, activity: AppCompatActivity, title: String, showBack: Boolean = true) {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(showBack)
            actionBar.title = title
        }
    }

}
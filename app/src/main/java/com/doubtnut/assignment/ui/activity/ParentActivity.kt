package com.doubtnut.assignment.ui.activity

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.core.base.application.BaseActivity
import com.doubtnut.assignment.R
import kotlinx.android.synthetic.main.activity_main.*


class ParentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp() =
            findNavController(nav_host_fragment).navigateUp()
}

package com.febers.iuestc

import android.os.Bundle
import com.febers.iuestc.lifecycle.IActivity
import com.febers.iuestc.util.StatusBarUtil

class HomeActivity: IActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBar(this, false, 0, false)
    }

    override fun afterResume() {
        super.afterResume()
    }

    override fun contentLayout(): Int = R.layout.activity_main
}
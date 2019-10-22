package com.febers.iuestc.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class IActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentLayout())
    }

    private var activityCreated = false

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!activityCreated && hasFocus) {
            activityCreated = true
            afterResume()
        }
    }

    open fun afterResume(){ }

    abstract fun contentLayout(): Int
}
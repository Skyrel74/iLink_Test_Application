package com.example.ilink_test_application.utils

import android.view.View

class DoubleClickListener(
    private val doubleClickTimeLimitMills: Long = 1000,
    private val callback: Callback,
) : View.OnClickListener {
    private var lastClicked: Long = -1L

    override fun onClick(v: View?) {
        lastClicked = when {
            lastClicked == -1L -> {
                System.currentTimeMillis()
            }
            isDoubleClicked() -> {
                callback.doubleClicked()
                -1L
            }
            else -> {
                System.currentTimeMillis()
            }
        }
    }

    private fun isDoubleClicked() =
        (System.currentTimeMillis() - lastClicked) <= doubleClickTimeLimitMills

    interface Callback {
        fun doubleClicked()
    }
}
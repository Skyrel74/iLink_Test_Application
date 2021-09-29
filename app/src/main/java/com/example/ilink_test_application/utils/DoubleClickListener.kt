package com.example.ilink_test_application.utils

import android.view.View

/**
 * Class to get when user is double-clicked
 *
 * @param[doubleClickTimeLimitMills] time between clicks
 * @param[callback] action after double-clicked
 *
 * @property[lastClicked] time of the last click
 */
class DoubleClickListener(
    private val doubleClickTimeLimitMills: Long = 1000,
    private val callback: Callback,
) : View.OnClickListener {
    private var lastClicked: Long = -1L

    /**
     * Function to save [lastClicked] time or run double-clicked [callback]
     */
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

    /**
     * Function to get state of double-click
     *
     * @return[Boolean]
     */
    private fun isDoubleClicked() =
        (System.currentTimeMillis() - lastClicked) <= doubleClickTimeLimitMills

    /**
     * Interface to make callback
     */
    interface Callback {
        fun doubleClicked()
    }
}

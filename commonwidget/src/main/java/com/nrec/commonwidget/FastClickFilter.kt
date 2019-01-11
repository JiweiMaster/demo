package com.nrec.commonwidget

import android.os.Handler
import android.os.Looper

/**
 * Created by 18099 on 2018/11/19.
 */

object FastClickFilter{
    private val DELAT_WHAT = 0x73628
    private val mainHandler = Handler(Looper.getMainLooper())
    private val delayMillis = 800L

    @JvmStatic
    fun filter(): Boolean =
            if(mainHandler.hasMessages(DELAT_WHAT)){
                false
            } else {
                mainHandler.sendEmptyMessageDelayed(DELAT_WHAT,delayMillis)
                true
            }
}


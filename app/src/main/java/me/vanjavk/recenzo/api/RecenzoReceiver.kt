package me.vanjavk.recenzo.api

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import me.vanjavk.recenzo.DATA_IMPORTED
import me.vanjavk.recenzo.HostActivity
import me.vanjavk.recenzo.framework.setBooleanPreference
import me.vanjavk.recenzo.framework.startActivity

class RecenzoReceiver : BroadcastReceiver() { // BroadcastReceiver aint no Context

    override fun onReceive(context: Context, intent: Intent) {

        context.startActivity<HostActivity>()
    }
}
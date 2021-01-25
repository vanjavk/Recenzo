package me.vanjavk.recenzo

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import me.vanjavk.recenzo.api.RecenzoFetcher

private const val JOB_ID = 1

class RecenzoService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        RecenzoFetcher(this).fetchItems()
    }
    companion object { // convenience
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, RecenzoService::class.java, JOB_ID, intent)
        }
    }
}
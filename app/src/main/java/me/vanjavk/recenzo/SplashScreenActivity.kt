package me.vanjavk.recenzo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import me.vanjavk.recenzo.framework.applyAnimation
import me.vanjavk.recenzo.framework.getBooleanPreference
import me.vanjavk.recenzo.framework.isOnline
import me.vanjavk.recenzo.framework.startActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

private const val DELAY : Long = 3000
const val DATA_IMPORTED  = "me.vanjavk.recenzo.data_imported"
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        ivSplash.applyAnimation(R.anim.rotate)
        tvSplash.applyAnimation(R.anim.enlarge)
    }

    private fun redirect() {

        if (getBooleanPreference(DATA_IMPORTED)) {
            Handler(Looper.getMainLooper()).postDelayed(
                {startActivity<HostActivity>()},
                DELAY
            )
        } else {
            if (isOnline()) {
                // start service
                Intent(this, RecenzoService::class.java).apply {
                    RecenzoService.enqueueWork(this@SplashScreenActivity, this)
                }
            } else {
                Toast.makeText(this, getString(R.string.please_connect_to_the_internet), Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }
}
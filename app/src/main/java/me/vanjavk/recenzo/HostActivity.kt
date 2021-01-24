package me.vanjavk.recenzo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class HostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//        setContentView(R.layout.activity_host)
setContentView(R.layout.activity_main)
//        initHamburgerMenu()
//        initNavigation()
    }

//    private fun initHamburgerMenu() {
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
//    }
//
//    private fun initNavigation() {
//        val navController = Navigation.findNavController(this, R.id.navHostFragment)
//        NavigationUI.setupWithNavController(navigationView, navController)
//    }
//
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.host_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            android.R.id.home -> {
//                toggleDrawer()
//                return true
//            }
//            R.id.menuExit -> {
//                exitApp()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun exitApp() {
//
//        AlertDialog.Builder(this).apply {
//            setTitle(R.string.exit)
//            setMessage(getString(R.string.really))
//            setIcon(R.drawable.exit)
//            setCancelable(true)
//            setPositiveButton("OK") {_, _ -> finish()}
//            setNegativeButton(getString(R.string.cancel), null)
//            show()
//        }
//    }
//
//    private fun toggleDrawer() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawers()
//        } else {
//            drawerLayout.openDrawer(GravityCompat.START)
//        }
//    }

}
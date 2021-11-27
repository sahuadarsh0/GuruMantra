package technited.minds.gurumantra.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.ActivityMainBinding
import technited.minds.gurumantra.ui.login.LoginActivity
import technited.minds.gurumantra.utils.SharedPrefs
import us.zoom.sdk.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    private var backPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GuruMantra)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val bottomNavigationView: BottomNavigationView = binding.appBarMain.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.mainNavView

        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.itemBackground = null
        navView.itemIconTintList = null
        navView.itemBackground = null
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_menu -> {
                    drawerLayout.openDrawer(Gravity.LEFT)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_test_series -> {
                    navController.navigate(
                        R.id.navigation_test_series,
                        bundleOf("type" to "test")
                    )
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_blogs -> {
                    navController.navigate(R.id.navigation_blogs)
                    return@setOnItemSelectedListener true
                }

                R.id.logout -> {
                    MaterialDialog(this).show {
                        title(text = "Logout?")
                        message(text = "Are you sure want to logout ?")
                        cornerRadius(16f)
                        positiveButton(text = "Yes") { dialog ->
                            userSharedPreferences.clearAll()
                            dialog.dismiss()
                            val i = Intent(this@MainActivity, LoginActivity::class.java)
                            startActivity(i)
                            finish()
                        }
                        negativeButton(text = "Cancel") { dialog ->
                            dialog.dismiss()
                        }
                    }
                    return@setOnItemSelectedListener true
                }
                else -> Toast.makeText(this, "Select an item", Toast.LENGTH_SHORT).show()
            }
            false
        }

        navView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()
            when (it.itemId) {
                R.id.navigation_gallery -> {
                    navController.navigate(R.id.navigation_gallery)
                }
                R.id.navigation_packages -> {
                    navController.navigate(R.id.navigation_packages)
                }
                R.id.navigation_practice -> {
                    navController.navigate(
                        R.id.navigation_test_series,
                        bundleOf("type" to "practice")
                    )
                }
            }
            true
        }

        val headerView = navView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.name).text = userSharedPreferences["name"]
        headerView.findViewById<TextView>(R.id.contact).text = userSharedPreferences["contact"]
        headerView.findViewById<TextView>(R.id.email).text = userSharedPreferences["email"]
        if (userSharedPreferences["phoneVerified"]?.toInt() == 1)
            headerView.findViewById<ImageView>(R.id.verified).visibility = VISIBLE
        else
            headerView.findViewById<ImageView>(R.id.verified).visibility = GONE
    }

    override fun onBackPressed() {
        if (navController.graph.startDestination == navController.currentDestination!!.id) {
            if (backPressedOnce) {
//                super.onBackPressed()
                finish()
            }
            backPressedOnce = true
            Toast.makeText(this, "Please Back again to exit", Toast.LENGTH_SHORT).show()

            val handler = Handler()
            handler.postDelayed({ backPressedOnce = false }, 2000)
        } else {
            super.onBackPressed()
        }
    }

}
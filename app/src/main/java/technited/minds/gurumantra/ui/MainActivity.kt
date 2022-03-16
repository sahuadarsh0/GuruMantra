package technited.minds.gurumantra.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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

        setSupportActionBar(binding.appBarMain.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var toggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            binding.appBarMain.toolBar,
            R.string.nav_app_bar_open_drawer_description,
            R.string.zm_bo_title_close
        )
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            binding.appBarMain.toolBar.setNavigationOnClickListener {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    super.onBackPressed()
                } else {
                    supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                    drawerLayout.addDrawerListener(toggle)
                    toggle.syncState()
                    drawerLayout.openDrawer(GravityCompat.START)
                }
            }
        } else {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
        }

        bottomNavigationView.setupWithNavController(navController)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bottomNavigationView.itemIconTintList = resources.getColorStateList(R.color.select_red, null)
            navView.itemIconTintList = resources.getColorStateList(R.color.select_red, null)
        }
        bottomNavigationView.itemBackground = null
        navView.itemBackground = null
        navView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()
            when (it.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                }
                R.id.navigation_about_us -> {
                    val intent = Intent(this@MainActivity, WebPage::class.java)
                    intent.putExtra("url", "https://gurumantra.online/api/about")
                    startActivity(intent)
                }
                R.id.navigation_coupons -> {
                    navController.navigate(R.id.navigation_coupons)
                }
                R.id.navigation_scholarship -> {
                    val intent = Intent(this@MainActivity, WebPage::class.java)
                    intent.putExtra("url", "https://gurumantra.online/api/scholarship?userId=${userSharedPreferences["id"]}")
                    startActivity(intent)
                }
                R.id.navigation_special_offers -> {
                    navController.navigate(R.id.navigation_special_offers)
                }
                R.id.navigation_live_class -> {
                    navController.navigate(R.id.navigation_live_class)
                }
                R.id.navigation_notes -> {
                    navController.navigate(R.id.navigation_library_notes)
                }
                R.id.navigation_test_series_type -> {
                    navController.navigate(R.id.navigation_test_series_type)
                }
                R.id.navigation_packages -> {
                    navController.navigate(R.id.navigation_packages)
                }
                R.id.navigation_courses -> {
                    navController.navigate(R.id.navigation_courses)
                }
                R.id.navigation_blogs -> {
                    navController.navigate(R.id.navigation_blogs)
                }
                R.id.navigation_gallery -> {
                    navController.navigate(R.id.navigation_gallery)
                }
                R.id.navigation_contact_us -> {
                    val intent = Intent(this@MainActivity, WebPage::class.java)
                    intent.putExtra("url", "https://gurumantra.online/contact")
                    startActivity(intent)
                }
                R.id.navigation_profile -> {
                    val intent = Intent(this@MainActivity, WebPage::class.java)
                    intent.putExtra("url", "https://gurumantra.online/api/profile/${userSharedPreferences["id"]}")
                    startActivity(intent)
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
                finish()
            }
            backPressedOnce = true
            Toast.makeText(this, "Please Back again to exit", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                backPressedOnce = false
            }, 2000)
        } else {
            super.onBackPressed()
        }
    }

}
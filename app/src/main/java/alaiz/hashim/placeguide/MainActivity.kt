package alaiz.hashim.placeguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView


lateinit var drawerLayout: DrawerLayout
lateinit var navView: NavigationView
lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fm: FragmentManager = supportFragmentManager
        val currentFragment = fm.findFragmentById(R.id.nav_host_fragment)

        if (currentFragment == null) {
            val fragment = InputFragment.newInstance()
            fm.beginTransaction()
                .add(R.id.nav_host_fragment, fragment)
                .commit()
        }
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val toolbar: Toolbar = findViewById(R.id.toolbar)





        actionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                drawerView.id
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

        }
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.hospital -> { moveToMapFragment(R.id.hospital,"Starred Fragment")
                    true
                }
                R.id.school -> {
                    moveToMapFragment(R.id.school,"Recent Fragment")
                    true
                }
                R.id.police_station -> {
                    moveToMapFragment(R.id.police_station,"Upload Fragment")
                    true
                }
                else -> {
                    false
                }
            }
        }


    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }
    fun moveToMapFragment(itemId:Int, title:String){
        val intent=Intent(this,MapsActivity::class.java)
        startActivity(intent)
        drawerLayout.closeDrawer(GravityCompat.START)
        Toast.makeText(this, "$title is opened", Toast.LENGTH_LONG).show()
    }

}
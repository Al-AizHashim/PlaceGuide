package alaiz.hashim.placeguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.navigation.NavigationView
import java.io.Serializable


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

                R.id.school -> {
                    moveToMapFragment(0, "school")
                    true
                }
                R.id.hospital -> {
                    moveToMapFragment(1, "Hospitals")
                    Toast.makeText(this,"the item id is : ",Toast.LENGTH_LONG).show()
                    true
                }
                R.id.police_station -> {
                    moveToMapFragment(2, "Police Station")
                    true
                }
                R.id.garden -> {
                    moveToMapFragment(3, "Garden")
                    true
                }
                R.id.my_location -> {
                    moveToMapFragment(4, "My Location")
                    true
                }
                else -> {
                    false
                }
            }
        }


    }



    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    fun moveToMapFragment(category: Int, title: String) {
        //Toast.makeText(this,"the item id is : $itemId",Toast.LENGTH_LONG).show()
        val intent = Intent(this, MapsActivity::class.java)
        intent.apply {
            putExtra("PlaceCategory", category)
            putExtra("title",title)
        }
        startActivity(intent)
        drawerLayout.closeDrawer(GravityCompat.START)
    }

}
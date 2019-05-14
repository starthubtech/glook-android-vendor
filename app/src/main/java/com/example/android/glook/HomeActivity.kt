package com.example.android.glook

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.android.glook.adapters.HomeListAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mAuth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        /**Initialize the Firebase Autentication object**/
        mAuth = FirebaseAuth.getInstance()
        //val listHomePoko: ArrayList<HomePoko> = ArrayList()

        /**Create and populate an array using the method @createListHomePoko**/
        var listHomePoko = createListHomePoko()

        //Set the layout Manager on the recyclerView object and set it to a GridView with a column span of 2
        recyclerView.layoutManager = GridLayoutManager(this, 2)


        val homeAdapter = HomeListAdapter(listHomePoko, this,{ partItem : HomePoko -> homeItemClicked(partItem) })
        homeAdapter.notifyDataSetChanged()
        recyclerView.adapter = homeAdapter

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }


    private fun createListHomePoko() : ArrayList<HomePoko> {
        var homeList = ArrayList<HomePoko>()
        homeList.add(HomePoko(R.drawable.ic_salary_svg, "Earnings"))
        homeList.add(HomePoko(R.drawable.ic_appointment_svg, "Appointments"))
        homeList.add(HomePoko(R.drawable.ic_credit_cards_payment_svg, "Payments"))
        homeList.add(HomePoko(R.drawable.ic_profile_svg, "Profile"))
        homeList.add(HomePoko(R.drawable.ic_services_svg, "Services"))
        homeList.add(HomePoko(R.drawable.ic_support_svg, "Help and Support"))
        return homeList
    }

    private fun homeItemClicked(homeItem : HomePoko) {
        Toast.makeText(this, " You Clicked: ${homeItem.actionDesc}", Toast.LENGTH_SHORT).show()

        if (homeItem.actionDesc == "Earnings"){startActivity(Intent(this, EarningsActivity::class.java))}
       // else if (homeItem.actionDesc == "Appointments"){startActivity(Intent(this,EarningsActivity::class.java ))}
        else if (homeItem.actionDesc == "Payments"){startActivity(Intent(this,AddCardActivity::class.java ))}
        //else if (homeItem.actionDesc == "Profile"){startActivity(Intent(this,ProfileActivity::class.java ))}
        else if (homeItem.actionDesc == "Services"){startActivity(Intent(this,AddServiceActivity::class.java ))}
        else{}


         // Launch second activity, pass part ID as string parameter
//        val activityIntent = Intent(this, PartDetailActivity::class.java)
//        //activityIntent.putExtra(Intent.EXTRA_TEXT, homeItem.id.toString())
//        startActivity(activityIntent)
    }


        override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_logout -> signOut()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun signOut() {
        mAuth!!.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_logout -> signOut()


        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

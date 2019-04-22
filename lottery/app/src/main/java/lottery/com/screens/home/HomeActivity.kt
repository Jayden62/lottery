package lottery.com.screens.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import lottery.com.R
import kotlinx.android.synthetic.main.activity_home.*
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import lottery.com.screens.fragments.news.FragmentNews
import lottery.com.screens.fragments.profile.FragmentProfile
import lottery.com.screens.fragments.service.FragmentService

class HomeActivity : AppCompatActivity() {

    private var toolbar: ActionBar? = null
    private var mFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar = supportActionBar
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        toolbar?.title = "Services"
        loadFragments(FragmentService())
    }

    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.mServiceMenu -> {
                    toolbar?.title = "Services"
                    mFragment = FragmentService()
                    loadFragments(mFragment as FragmentService)
                    return true
                }
                R.id.mNewsMenu -> {
                    toolbar?.title = "Daily News"
                    mFragment = FragmentNews()
                    loadFragments(mFragment as FragmentNews)
                    return true
                }
                R.id.mProfileMenu -> {
                    toolbar?.title = "Profile"
                    mFragment = FragmentProfile()
                    loadFragments(mFragment as FragmentProfile)
                    return true
                }

            }
            return false
        }
    }

    private fun loadFragments(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}

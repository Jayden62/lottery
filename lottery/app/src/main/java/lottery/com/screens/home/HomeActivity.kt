package lottery.com.screens.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import lottery.com.R
import kotlinx.android.synthetic.main.activity_home.*
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import lottery.com.database.DBHelper
import lottery.com.model.User
import lottery.com.screens.fragments.news.FragmentNews
import lottery.com.screens.fragments.profile.FragmentProfile
import lottery.com.screens.fragments.typeservice.FragmentTypeService
import lottery.com.helper.Constants

class HomeActivity : AppCompatActivity() {

    private var toolbar: ActionBar? = null
    private var mFragment: Fragment? = null

    private var data: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar = supportActionBar
        data = intent.getParcelableExtra(Constants.Data.DATA)
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        toolbar?.title = "Services"
        loadFragments(FragmentTypeService())
    }


    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.mServiceMenu -> {
                    toolbar?.title = "Services"
                    mFragment = FragmentTypeService()
                    loadFragments(mFragment as FragmentTypeService)
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
                    val bundle = Bundle()
                    bundle.putParcelable(Constants.Data.DATA, data)
                    (mFragment as FragmentProfile).arguments = bundle
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

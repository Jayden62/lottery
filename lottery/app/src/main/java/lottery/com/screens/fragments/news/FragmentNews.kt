package lottery.com.screens.fragments.news


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_news.*
import lottery.com.R
import lottery.com.adapter.ViewPagerAdapter
import lottery.com.database.DBHelper
import java.util.*
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.util.Log
import android.widget.TextView

class FragmentNews : Fragment() {

    private var mViewPagerAdapter: ViewPagerAdapter? = null

    private var images: MutableList<String> = mutableListOf()

    private var emptyImages: MutableList<String> =
        mutableListOf("https://jackholesrealm.files.wordpress.com/2011/09/e786ba29e2e8b342651c45b7da913dc9-blank-sticky-note-clip-art.jpg")

    private var currentPage = 0

    private val DELAY_MS: Long = 500

    private val PERIOD_MS: Long = 2500

    private var mViewPager: ViewPager? = null

    private var mTextViewName: TextView? = null

    private var mTextViewContent: TextView? = null

    private var mTextViewStartValue: TextView? = null

    private var mTextViewEndValue: TextView? = null

    companion object {
        val TAG = this.javaClass.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewPager = view.findViewById(R.id.mViewPager)
        mTextViewName = view.findViewById(R.id.mTextViewName)
        mTextViewContent = view.findViewById(R.id.mTextViewContent)
        mTextViewStartValue = view.findViewById(R.id.mTextViewStartValue)
        mTextViewEndValue = view.findViewById(R.id.mTextViewEndValue)
        onInit()
    }

    private fun onInit() {
        val data = DBHelper().getNews()
        if (data?.size != 0) {
            for (item in data!!) {
                mTextViewName?.text = item.name
                mTextViewContent?.text = item.detail
                mTextViewStartValue?.text = item.startDate
                mTextViewEndValue?.text = item.endDate
                images.add(item.image)
            }

            mViewPagerAdapter = if (images.isNullOrEmpty()) {
                ViewPagerAdapter(activity!!, emptyImages)
            } else {
                ViewPagerAdapter(activity!!, images)
            }
            mViewPager?.adapter = mViewPagerAdapter
            val handler = Handler()
            val update = Runnable {
                if (currentPage == images.size) {
                    currentPage = 0
                }
                mViewPager?.setCurrentItem(currentPage++, true)
            }

            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    handler.post(update)
                }
            }, DELAY_MS, PERIOD_MS)

            mViewPager?.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    Log.d(TAG, position.toString())
                }

                override fun onPageSelected(position: Int) {
                    Log.d(TAG, position.toString())
                    DBHelper().getNews()?.forEachIndexed { index, item ->
                        if (position == index) {
                            mTextViewName?.text = item.name
                            mTextViewContent?.text = item.detail
                            mTextViewStartValue?.text = item.startDate
                            mTextViewEndValue?.text = item.endDate
                        }
                    }

                }
            })
        }
    }
}

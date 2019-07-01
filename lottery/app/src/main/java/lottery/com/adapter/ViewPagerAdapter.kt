package lottery.com.adapter

import android.app.Activity
import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import lottery.com.R

class ViewPagerAdapter(var context: Context, var images: MutableList<String>) : PagerAdapter() {
    private var inflater: LayoutInflater? = null

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater!!.inflate(R.layout.item_viewpager, container, false)

        val mImageViewNews = itemView.findViewById(R.id.mImageViewNews) as ImageView
        mImageViewNews.let {
            Glide.with(context)
                .load(images[position])
                .into(it)
        }

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }
}

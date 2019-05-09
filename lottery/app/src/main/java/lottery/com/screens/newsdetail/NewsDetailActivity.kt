package lottery.com.screens.newsdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import lottery.com.R
import lottery.com.helper.Constants
import lottery.com.model.News
import kotlinx.android.synthetic.main.activity_news_detail.*
import lottery.com.helper.DateHelper

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val value = intent.getParcelableExtra(Constants.Data.DATA) as News
        mImageViewNews?.let {
            Glide.with(this)
                .load(value.image)
                .into(it)
        }
        mTextViewTitle.text = value.name
        mTextViewDetail.text = value.detail
        mTextViewStartDate.text = value.startDate
        mTextViewEndDate.text = value.endDate
    }
}

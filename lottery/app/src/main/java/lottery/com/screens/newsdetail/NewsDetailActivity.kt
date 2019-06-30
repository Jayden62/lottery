package lottery.com.screens.newsdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import lottery.com.R
import lottery.com.utils.Constants
import lottery.com.model.News
import kotlinx.android.synthetic.main.activity_news_detail.*
import lottery.com.utils.DialogUtils

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        val mProgressDialog = DialogUtils.showLoadingDialog(this, this.getString(R.string.loading_data))
        val value = intent.getParcelableExtra(Constants.Data.DATA) as News
        mImageViewNews?.let {
            Glide.with(this)
                .load(this.getDrawable(R.drawable.photo_news))
                .into(it)
        }
        mTextViewTitle.text = value.name
        mTextViewDetail.text = value.detail
        mTextViewStartDate.text = value.startDate
        mTextViewEndDate.text = value.endDate
        Handler().postDelayed({ mProgressDialog.dismiss() }, 1500)

    }
}

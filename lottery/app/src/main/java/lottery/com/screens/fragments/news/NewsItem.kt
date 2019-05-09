package lottery.com.screens.fragments.news

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import lottery.com.R
import lottery.com.base.list.BaseItem
import lottery.com.model.News
import kotlinx.android.synthetic.main.item_news.view.*

class NewsItem(var context: Context, var value: News?, var callback: Callback) : BaseItem<Any>(context),
    View.OnClickListener {


    override fun onInitLayout(): Int = R.layout.item_news

    interface Callback {
        fun onTapItem(data: News?)
    }

    override fun onBindView(view: View?) {
        view?.mImageViewNews?.let {
            Glide.with(mContext)
                .load(value?.image)
                .into(it)
        }
        view?.mTextViewTitle?.text = value?.name

        view?.mTextViewTitle?.setOnClickListener(this)
        view?.mImageViewNews?.setOnClickListener(this)
        view?.mConstrainLayout?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mTextViewTime, R.id.mImageViewName, R.id.mConstrainLayout -> {
                callback.onTapItem(value)
            }
        }
    }

}
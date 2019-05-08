package lottery.com.screens.fragments.news

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem

class NewsItem(context: Context) : BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_news

    override fun onBindView(view: View?) {
    }

}
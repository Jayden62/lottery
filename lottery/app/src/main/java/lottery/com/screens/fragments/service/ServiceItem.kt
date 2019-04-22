package lottery.com.screens.fragments.service

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem

class ServiceItem(context: Context) : BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_service

    override fun onBindView(view: View?) {
    }
}
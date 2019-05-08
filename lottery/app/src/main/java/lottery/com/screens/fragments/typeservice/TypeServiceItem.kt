package lottery.com.screens.fragments.typeservice

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_service.view.*
import lottery.com.helper.DataHelper
import lottery.com.helper.DateHelper
import lottery.com.model.TypeService

class TypeServiceItem(var context: Context, var typeService: TypeService?, var callback: Callback) :
    BaseItem<Any>(context), View.OnClickListener {

    interface Callback {
        fun onTapItem()
    }

    override fun onInitLayout(): Int = R.layout.item_service

    override fun onBindView(view: View?) {
        view?.mConstrainLayout?.setOnClickListener(this)

        view?.mTextViewName?.text = DataHelper.initText(typeService?.name.toString())
        view?.mImageView?.let {
            Glide.with(mContext)
                .load(typeService?.image)
                .apply(RequestOptions.circleCropTransform())
                .into(it)
        }
    }

    override fun onClick(v: View?) {
        callback.onTapItem()
    }

}
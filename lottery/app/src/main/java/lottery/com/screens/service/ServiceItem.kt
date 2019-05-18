package lottery.com.screens.service

import android.content.Context
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.util.Log
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_sub_service.view.*
import android.view.animation.RotateAnimation
import android.widget.*
import lottery.com.database.DBHelper
import lottery.com.helper.Dialog
import lottery.com.model.Service


class ServiceItem(var context: Context, var value: Service?, var callback: Callback) : BaseItem<Any>(context),
    View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

    interface Callback {
        fun onCheckItem(value: Service?)
    }

    private var isRotated = false

    private var mImageView: ImageView? = null
    private var mConstrainLayout: ConstraintLayout? = null
    private var mTextViewName: TextView? = null
    private var mTextViewPromotion: TextView? = null
    private var mTextViewPriceLabel: TextView? = null
    private var mTextViewPrice: TextView? = null
    private var mTextViewTimeLabel: TextView? = null
    private var mTextViewTime: TextView? = null
    private var mTextViewDesLabel: TextView? = null
    private var mTextViewDes: TextView? = null
    private var mCheckBox: CheckBox? = null
    private var mViewLine: View? = null

    private var mCardView: CardView? = null

    val TAG = "ServiceItem"

    override fun onInitLayout(): Int = R.layout.item_sub_service

    override fun onBindView(view: View?) {

        mConstrainLayout = view?.mConstrainLayout
        mImageView = view?.mImageView
        mTextViewName = view?.mTextViewName
        mTextViewPromotion = view?.mTextViewPromotion
        mTextViewPrice = view?.mTextViewPrice
        mTextViewPriceLabel = view?.mTextViewPriceLabel
        mTextViewTime = view?.mTextViewTime
        mTextViewTimeLabel = view?.mTextViewTimeLabel
        mTextViewDes = view?.mTextViewDes
        mTextViewDesLabel = view?.mTextViewDesLabel
        mCheckBox = view?.mCheckBox
        mViewLine = view?.mViewLine
        mCardView = view?.mCardView

        mConstrainLayout?.setOnClickListener(this)
        mImageView?.setOnClickListener(this)
        mTextViewName?.setOnClickListener(this)
        mTextViewPrice?.setOnClickListener(this)
        mTextViewPriceLabel?.setOnClickListener(this)
        mTextViewTime?.setOnClickListener(this)
        mTextViewTimeLabel?.setOnClickListener(this)
        mTextViewDes?.setOnClickListener(this)
        mTextViewDesLabel?.setOnClickListener(this)

        mCheckBox?.setOnCheckedChangeListener(this)

        mTextViewName?.text = value?.name
        mTextViewPrice?.text = value?.price?.toString() + " VND "
        mTextViewTime?.text = value?.timeTodo.toString() + " phút "
        mTextViewDes?.text = value?.detail

        if (value?.id == DBHelper().getServicePromotion()) {
            mTextViewPromotion?.visibility = View.VISIBLE
            mTextViewPromotion?.text = "Đang khuyến mãi"
        } else {
            mTextViewPromotion?.visibility = View.GONE
        }

        isRotated = true
    }

    private fun rotatingAnimation(degree: Float) {
        val rotateAnim = RotateAnimation(
            0.0f, degree,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f
        )

        rotateAnim.duration = 0
        rotateAnim.fillAfter = true
        mImageView?.startAnimation(rotateAnim)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mCheckBox?.isChecked!!) {
                callback.onCheckItem(value)
                mCardView?.setBackgroundColor(mContext.getColor(R.color.grayColor))
            } else {
                mCardView?.setBackgroundColor(mContext.getColor(R.color.colorWhite))
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mConstrainLayout,
            R.id.mImageView,
            R.id.mTextViewName,
            R.id.mTextViewPrice,
            R.id.mTextViewPriceLabel,
            R.id.mTextViewTime,
            R.id.mTextViewTimeLabel,
            R.id.mTextViewDesLabel -> {
                isRotated = when (isRotated) {
                    true -> {
                        rotatingAnimation(180f)
                        mTextViewPrice?.visibility = View.VISIBLE
                        mTextViewPriceLabel?.visibility = View.VISIBLE
                        mTextViewTime?.visibility = View.VISIBLE
                        mTextViewTimeLabel?.visibility = View.VISIBLE
                        mTextViewDes?.visibility = View.VISIBLE
                        mTextViewDesLabel?.visibility = View.VISIBLE
                        mViewLine?.visibility = View.VISIBLE
                        Log.d(TAG, isRotated.toString())
                        false
                    }
                    false -> {
                        rotatingAnimation(0f)
                        mTextViewPrice?.visibility = View.GONE
                        mTextViewPriceLabel?.visibility = View.GONE
                        mTextViewTime?.visibility = View.GONE
                        mTextViewTimeLabel?.visibility = View.GONE
                        mTextViewDes?.visibility = View.GONE
                        mTextViewDesLabel?.visibility = View.GONE
                        mViewLine?.visibility = View.INVISIBLE
                        Log.d(TAG, isRotated.toString())
                        true
                    }
                }
            }


            R.id.mTextViewDes -> {
                Dialog.MessageDialog.showMessageDialog(value?.detail!!, mContext)
            }
        }
    }
}
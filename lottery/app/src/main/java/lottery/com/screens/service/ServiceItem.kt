package lottery.com.screens.service

import android.annotation.SuppressLint
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
import lottery.com.utils.DialogUtils
import lottery.com.model.Service
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.text.style.UnderlineSpan
import android.text.SpannableString

class ServiceItem(var context: Context, var value: Service?, private var callback: Callback) : BaseItem<Any>(context),
    View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {


    interface Callback {
        fun onServiceItemSelected(value: Service?)
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


    private var mImageViewCheck: ImageView? = null

    private var mCardView: CardView? = null

    private var slideUp: Animation? = null
    private var slideDown: Animation? = null
    val TAG = "ServiceItem"

    override fun onInitLayout(): Int = R.layout.item_sub_service

    @SuppressLint("SetTextI18n")
    override fun onBindView(view: View?) {

        mConstrainLayout = view?.mConstrainLayout
        mImageView = view?.mImageViewOn
        mTextViewName = view?.mTextViewName
        mTextViewPrice = view?.mTextViewPrice
        mTextViewPriceLabel = view?.mTextViewPriceLabel
        mTextViewTime = view?.mTextViewTime
        mTextViewTimeLabel = view?.mTextViewTimeLabel
        mTextViewDes = view?.mTextViewDes
        mTextViewDesLabel = view?.mTextViewDesLabel
        mCardView = view?.mCardView
        mImageViewCheck = view?.mImageViewCheck


        slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)

        mConstrainLayout?.setOnClickListener(this)
        mImageView?.setOnClickListener(this)
        mTextViewName?.setOnClickListener(this)
        mTextViewPrice?.setOnClickListener(this)
        mTextViewPriceLabel?.setOnClickListener(this)
        mTextViewTime?.setOnClickListener(this)
        mTextViewTimeLabel?.setOnClickListener(this)
        mTextViewDes?.setOnClickListener(this)
        mTextViewDesLabel?.setOnClickListener(this)

        val content = SpannableString(value?.name)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        mTextViewName?.text = content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTextViewName?.setTextColor(context.getColor(R.color.colorBlack))
        }
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

            } else {
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mImageViewOn, R.id.mTextViewName -> {
                isRotated = when (isRotated) {
                    true -> {
                        rotatingAnimation(180f)
                        mTextViewPrice?.visibility = View.VISIBLE
                        mTextViewPriceLabel?.visibility = View.VISIBLE
                        mTextViewTime?.visibility = View.VISIBLE
                        mTextViewTimeLabel?.visibility = View.VISIBLE
                        mTextViewDes?.visibility = View.VISIBLE
                        mTextViewDesLabel?.visibility = View.VISIBLE
                        mImageViewCheck?.visibility = View.VISIBLE
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            mTextViewName?.setTextColor(context.getColor(R.color.selectedDateColor))
//                            mCardView?.setCardBackgroundColor(context.getColor(R.color.activeCard))
                        }

                        mTextViewName?.isEnabled = false
                        mImageView?.isEnabled = false
                        callback.onServiceItemSelected(value)
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
                        mImageViewCheck?.visibility = View.GONE


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            mTextViewName?.setTextColor(context.getColor(R.color.colorBlack))
                        }
                        Log.d(TAG, isRotated.toString())
                        true
                    }
                }
            }

            R.id.mTextViewDes -> {
                DialogUtils.showMessageDialog(value?.detail!!, mContext)
            }

        }
    }
}
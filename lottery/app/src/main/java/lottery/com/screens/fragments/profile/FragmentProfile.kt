package lottery.com.screens.fragments.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import lottery.com.R
import lottery.com.database.DBHelper
import lottery.com.model.User
import lottery.com.screens.information.InformationActivity
import lottery.com.utils.Constants
import lottery.com.utils.DialogUtils

class FragmentProfile : Fragment(), View.OnClickListener, TextWatcher, CompoundButton.OnCheckedChangeListener {


    var data: User? = null

    private var mSwitchReminder: Switch? = null
    private var mTextViewReminder: TextView? = null
    private var mTextViewPhone: TextView? = null
    private var mButtonInfo: Button? = null

    private var phoneNumber: String? = null

    private val TAG = "FragmentProfile"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSwitchReminder = view.mSwitchReminder
        mTextViewReminder = view.mTextViewReminder
        mTextViewPhone = view.mTextViewPhone
        mButtonInfo = view.mButtonInfo

        mSwitchReminder?.setOnCheckedChangeListener(this)
        mButtonInfo?.setOnClickListener(this)
        data = arguments?.getParcelable(Constants.Data.DATA) as? User

//        if (data != null) {
//            view.mEditTextName.setText(data?.name)
        mTextViewPhone?.text = data?.phoneNumber
        phoneNumber = data?.phoneNumber
//            view.mEditTextSex.setText(data?.sex)
//            view.mEditTextAddress.setText(data?.address)
//        }

    }

    @SuppressLint("ResourceAsColor")
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (isChecked) {
            true -> {
                mTextViewReminder?.setTextColor(resources.getColor(R.color.colorAccent))
                mTextViewReminder?.text = "Lịch hẹn đã được đặt"
            }
            false -> {
                mTextViewReminder?.setTextColor(resources.getColor(R.color.colorGray))
                mTextViewReminder?.text = "Mở tự động đặt lịch"
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mButtonInfo -> {
                getInfoDialog(phoneNumber)
            }
        }
    }

    private fun getInfoDialog(phoneNumber: String?) {
        val dialog = AlertDialog.Builder(activity)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.dialog_get_info, null)
        dialog.setView(view)
        val mButtonClose = view.findViewById(R.id.mButtonBook) as Button
        val mButtonSend = view.findViewById(R.id.mButtonSend) as Button
        val mEditTextPwd = view.findViewById(R.id.mEditTextPwd) as EditText
        val mImageViewOn = view.findViewById(R.id.mImageViewOn) as ImageView
        val mImageViewOff = view.findViewById(R.id.mImageViewOff) as ImageView
        val alertDialog = dialog.create()

        mButtonClose.setOnClickListener { alertDialog.dismiss() }
        mButtonSend.setOnClickListener {
            if (TextUtils.isEmpty(
                    mEditTextPwd.text
                )
            ) {
                DialogUtils.showMessageDialog("Vui lòng nhập mật khẩu !", activity!!)
            } else {
                val data = DBHelper().getPassword(mEditTextPwd.text.toString(), phoneNumber)
                if (data != null) {
                    val intent = Intent(activity, InformationActivity::class.java)
                    intent.putExtra(Constants.Data.DATA, data)
                    startActivity(intent)
                } else {
                    DialogUtils.showMessageDialog("Sai mật khẩu !", activity!!)
                }
            }
        }
        mImageViewOn.setOnClickListener {
            mImageViewOn.visibility = View.GONE
            mImageViewOff.visibility = View.VISIBLE
            mEditTextPwd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }

        mImageViewOff.setOnClickListener {
            mImageViewOff.visibility = View.GONE
            mImageViewOn.visibility = View.VISIBLE
            mEditTextPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        }

        dialog.setCancelable(true)
        alertDialog.show()
    }
}

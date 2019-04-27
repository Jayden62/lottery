package lottery.com.screens.fragments.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

import lottery.com.R
import lottery.com.database.DBHelper
import lottery.com.model.User
import lottery.com.utils.Constants


class FragmentProfile : Fragment(), View.OnClickListener, TextWatcher {


    var data: User? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view?.mImageViewName.setOnClickListener(this)
        view?.mButtonSave.setOnClickListener(this)
        view?.mTextViewChangePassword?.setOnClickListener(this)
        data = arguments?.getParcelable(Constants.Data.DATA) as? User

        if (data != null) {
            view?.mEditTextName.setText(data?.name)
            view?.mEditTextPhone.setText(data?.phoneNumber)
            view?.mEditTextSex.setText(data?.sex)
            view?.mEditTextAddress.setText(data?.address)
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
            R.id.mImageViewName -> {
                mEditTextName.isEnabled = true
                mEditTextAddress.isEnabled = true
                mButtonSave.isEnabled = true
            }
            R.id.mTextViewChangePassword -> {
                openDialogChangePassword()
            }
        }
    }

    private fun openDialogChangePassword() {
        val dialog = AlertDialog.Builder(activity)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.change_password_dialog, null)
        dialog.setView(view)
        val mButtonClose = view.findViewById(R.id.mButtonClose) as Button
        val mButtonSend = view.findViewById(R.id.mButtonSend) as Button
        val mEditTextOldPasword = view.findViewById(R.id.mEditTextOldPassword) as EditText
        val mEditTextNewPasword = view.findViewById(R.id.mEditTextNewPassword) as EditText
        val mEditTextConfirmPasword = view.findViewById(R.id.mEditTextConfirmPassword) as EditText
        val mTextViewMessage = view.findViewById(R.id.mTextViewMessage) as TextView

        val alertDialog = dialog.create()
        mButtonSend.isEnabled = false

        mButtonClose.setOnClickListener { alertDialog.dismiss() }
        mButtonSend.setOnClickListener {

            alertDialog.dismiss()
        }

        mEditTextOldPasword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mButtonSend.isEnabled = s.toString() != "" || s.toString().isNotEmpty()
                when (DBHelper().checkOldPassword(s.toString())) {
                    false -> {
                        mTextViewMessage.text = "Password not match !"
                        mTextViewMessage.setTextColor(resources.getColor(R.color.redColor))
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        }
        )
        dialog.setCancelable(true)
        alertDialog.show()
    }
}

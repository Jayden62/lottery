package lottery.com.screens.fragments.profile

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
import lottery.com.helper.Constants
import lottery.com.helper.Dialog


class FragmentProfile : Fragment(), View.OnClickListener, TextWatcher {

    var data: User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.mImageViewName.setOnClickListener(this)
        view.mImageViewAddress.setOnClickListener(this)
        view.mButtonSave.setOnClickListener(this)
        view.mTextViewChangePassword?.setOnClickListener(this)
        data = arguments?.getParcelable(Constants.Data.DATA) as? User

        if (data != null) {
            view.mEditTextName.setText(data?.name)
            view.mEditTextPhone.setText(data?.phoneNumber)
            view.mEditTextSex.setText(data?.sex)
            view.mEditTextAddress.setText(data?.address)
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
                mButtonSave.isEnabled = true
            }

            R.id.mImageViewAddress -> {
                mEditTextAddress.isEnabled = true
                mButtonSave.isEnabled = true
            }

            R.id.mButtonSave -> {
                updateData()
            }
            R.id.mTextViewChangePassword -> {
                openDialogChangePassword()
            }
        }
    }

    private fun updateData() {
        when (DBHelper().updateProfile(
            data?.phoneNumber.toString(),
            mEditTextName.text.toString(),
            mEditTextAddress.text.toString()
        )) {
            true -> {
                Dialog.MessageDialog.showMessageDialog("update successfully .", this!!.activity!!)
                mEditTextName.isEnabled = false
                mEditTextAddress.isEnabled = false
                mButtonSave.isEnabled = false
            }
            false -> {
                Dialog.MessageDialog.showMessageDialog("update failed .", this!!.activity!!)
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

        mEditTextOldPasword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mButtonSend.isEnabled = s.toString() != "" || s.toString().isNotEmpty()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        mButtonClose.setOnClickListener { alertDialog.dismiss() }
        mButtonSend.setOnClickListener {
            val password = mEditTextOldPasword.text.toString()
            val newPassword = mEditTextNewPasword.text.toString()
            val confirmPassword = mEditTextConfirmPasword.text.toString()

            if (newPassword == confirmPassword) {
                when (DBHelper().updatePassword(data?.phoneNumber.toString(), password, newPassword)) {
                    true -> {
                        Dialog.MessageDialog.showMessageDialog("your password is updated.", this.activity!!)
                    }
                    false -> {
                        Dialog.MessageDialog.showMessageDialog("old password incorrect.", this.activity!!)
                    }
                }
            } else {
                Dialog.MessageDialog.showMessageDialog("new password not match.", this.activity!!)
            }
            alertDialog.dismiss()
        }
        dialog.setCancelable(true)
        alertDialog.show()
    }
}

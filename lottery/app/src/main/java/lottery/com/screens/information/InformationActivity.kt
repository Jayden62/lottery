package lottery.com.screens.information

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_infomation.*
import lottery.com.R
import lottery.com.database.DBHelper
import lottery.com.model.User
import lottery.com.utils.Constants
import lottery.com.utils.DialogUtils

class InformationActivity : AppCompatActivity(), View.OnClickListener {

    private var data: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infomation)

        mImageViewBack?.setOnClickListener(this)
        mTextViewChangePassword?.setOnClickListener(this)
        mButtonUpdate?.setOnClickListener(this)
        if (intent.hasExtra(Constants.Data.DATA)) {
            data = intent.getParcelableExtra(Constants.Data.DATA) as? User

            if (data != null) {
                mEditTextName.setText(data?.name)
                mEditTextPhone?.setText(data?.phoneNumber)
                mEditTextSex.setText(data?.sex)
                mEditTextAddress.setText(data?.address)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mImageViewBack -> {
                finish()
            }
            R.id.mTextViewChangePassword -> {
                openDialogChangePassword()
            }
            R.id.mButtonUpdate -> {
                updateData()
            }
        }
    }

    private fun openDialogChangePassword() {
        val dialog = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.change_password_dialog, null)
        dialog.setView(view)
        val mButtonClose = view.findViewById(R.id.mButtonBook) as Button
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
                        DialogUtils.showMessageDialog("Mật khẩu đã được cập nhật.", this)
                        alertDialog.dismiss()
                    }
                    false -> {
                        DialogUtils.showMessageDialog("Mật khẩu cũ không chính xác !", this)
                    }
                }
            } else {
                DialogUtils.showMessageDialog("Mật khẩu mới không trùng khớp.", this)
            }
        }
        dialog.setCancelable(true)
        alertDialog.show()
    }

    private fun updateData() {
        when (DBHelper().updateProfile(
            data?.phoneNumber.toString(),
            mEditTextName.text.toString(),
            mEditTextAddress.text.toString()
        )) {
            true -> {
                DialogUtils.showMessageDialog("Cập nhật thành công.", this)
                mButtonUpdate.text = "Cập nhật"
            }
            false -> {
                DialogUtils.showMessageDialog("Cập nhật thất bại, vui lòng thử lại.", this)
            }
        }
    }


}

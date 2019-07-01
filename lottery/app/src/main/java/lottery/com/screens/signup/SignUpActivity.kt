package lottery.com.screens.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_profile.*
import lottery.com.R
import lottery.com.database.DBHelper
import lottery.com.model.User
import lottery.com.screens.signin.SignInActivity
import lottery.com.utils.Constants
import lottery.com.utils.DialogUtils
import java.util.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener, TextWatcher, RadioGroup.OnCheckedChangeListener {

    private var sex: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mButtonRegister.setOnClickListener(this)
        mEditTextName.addTextChangedListener(this)
        mRadioGroup.setOnCheckedChangeListener(this)

        sex = mRadioMale.text.toString()


    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.mRadioMale -> {
                sex = mRadioMale.text.toString()
            }
            R.id.mRadioFemale -> {
                sex = mRadioFemale.text.toString()
            }
        }
    }

    private fun validate(): Boolean {
        if (mEditTextName.text.isEmpty() || mEditTextName.text == null) {
            DialogUtils.showMessageDialog("Vui lòng nhập họ và tên.", this)
            return false
        }
        if (mEditTextPhone.text.isEmpty() || mEditTextPhone.text == null) {
            DialogUtils.showMessageDialog("Vui lòng nhập số điện thoại.", this)
            return false
        }

        if (mEditTextPhone.text.length > 10 || mEditTextPhone.text.length < 10) {
            DialogUtils.showMessageDialog("Số điện thoại không đủ 10 số.", this)
            return false
        }
        if (mEditTextPassword.text.isEmpty() || mEditTextPassword.text == null) {
            DialogUtils.showMessageDialog("Vui lòng nhập mật khẩu.", this)
            return false
        }
//        if (mEditTextPassword.text.length < 6) {
//            DialogUtils.showMessageDialog("Mật khẩu ít nhất 6 kí tự.", this)
//            return false
//        }
        if (mEditTextAddress.text.isEmpty() || mEditTextAddress.text == null) {
            DialogUtils.showMessageDialog("Vui lòng nhập địa chỉ.", this)
            return false
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mButtonRegister -> {
                val mProgressDialog = DialogUtils.showLoadingDialog(this, this.getString(R.string.handling_data))

                val user = User(
                    mEditTextName.text.toString(),
                    mEditTextPhone.text.toString(),
                    mEditTextPassword.text.toString(),
                    sex!!,
                    mEditTextAddress.text.toString(),
                    UUID.randomUUID().toString()
                )

                if (validate()) {
                    val result = DBHelper().checkUserId(mEditTextPhone.text.toString(), user)
                    when (result) {
                        true -> {
                            clearData()
                            Handler().postDelayed({ mProgressDialog.dismiss() }, 1500)
                            val intent = Intent(this, SignInActivity::class.java)
                            intent.putExtra(Constants.Data.DATA, user)
                            startActivity(intent)
                        }
                        false -> {
                            DialogUtils.showMessageDialog("Số điện thoại đã tồn tại !", this)
                            mProgressDialog.dismiss()
                        }
                    }

                } else {
                    mProgressDialog.dismiss()
                }
            }
        }

    }

    private fun clearData() {
        mEditTextName.setText("")
        mEditTextPhone.setText("")
        mEditTextPassword.setText("")
        mEditTextAddress.setText("")
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        mButtonRegister.isEnabled = s.toString() != "" || s.toString().isNotEmpty()

    }

}

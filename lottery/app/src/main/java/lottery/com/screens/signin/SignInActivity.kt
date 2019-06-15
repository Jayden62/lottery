package lottery.com.screens.signin

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_sign_in.*
import lottery.com.R
import lottery.com.database.DBHelper
import lottery.com.model.User
import lottery.com.screens.home.HomeActivity
import lottery.com.screens.signup.SignUpActivity
import lottery.com.utils.Constants
import lottery.com.utils.DialogUtils
import lottery.com.utils.PreferenceHelper

class SignInActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {


    private val TAG = "SignInActivity"

    private var data: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        captureOnClick()
        getData()

    }


    private fun getData() {

        data = intent.getParcelableExtra(Constants.Data.DATA)

        if (data != null) {
            mEditTextPhone.setText(data?.phoneNumber)
            mEditTextPassword.setText(data?.passWord)
        }
    }

    private fun captureOnClick() {
        mButtonSignIn.setOnClickListener(this)
        mTextViewForgetPwd.setOnClickListener(this)
        mTextViewRegister.setOnClickListener(this)
        mEditTextPhone.addTextChangedListener(this)
    }

    private fun validate(): Boolean {
        if (mEditTextPhone.text.isEmpty() || mEditTextPhone.text == null) {
            DialogUtils.showMessageDialog("Vui lòng nhập số điện thoại.", this)
            return false
        }
        if (mEditTextPassword.text.isEmpty() || mEditTextPassword.text == null) {
            DialogUtils.showMessageDialog("Vui lòng nhập mật khẩu .", this)
            return false
        }

        return true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mButtonSignIn -> {
                if (validate()) {
                    when (DBHelper().userLogin(mEditTextPhone.text.toString(), mEditTextPassword.text.toString())) {
                        true -> {
                            if (data != null) {
                                val intent = Intent(this, HomeActivity::class.java)
                                intent.putExtra(Constants.Data.DATA, data)
                                PreferenceHelper.saveUser(this, data)
                                startActivity(intent)
                                finish()
                            } else {
                                val result = DBHelper().getUserByPhone(mEditTextPhone.text.toString())
                                if (result != null) {
                                    val intent = Intent(this, HomeActivity::class.java)
                                    intent.putExtra(Constants.Data.DATA, result)
                                    PreferenceHelper.saveUser(this, result)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }
                        false -> {
                            DialogUtils.showMessageDialog("Sai tên đăng nhập hoặc mật khẩu !", this)
                        }
                    }
                }
            }
            R.id.mTextViewForgetPwd ->
                /**
                 * Open dialog
                 */
                getPasswordDialog()
            R.id.mTextViewRegister -> {
                /**
                 * Move to SignUp screen
                 */
                val intentText = Intent(this, SignUpActivity::class.java)
                startActivity(intentText)
            }
        }
        /**
         * Check user login
         */
    }

    private fun getPasswordDialog() {
        val dialog = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.forgot_password_dialog, null)
        dialog.setView(view)
        val mButtonClose = view.findViewById(R.id.mButtonBook) as Button
        val mButtonSend = view.findViewById(R.id.mButtonSend) as Button
        val mEditTextPhone = view.findViewById(R.id.mEditTextPhone) as EditText
        val alertDialog = dialog.create()
        mButtonSend.isEnabled = false

        mButtonClose.setOnClickListener { alertDialog.dismiss() }
        mButtonSend.setOnClickListener {
            val phoneNumber = mEditTextPhone.text.toString()
            val passWord = DBHelper().forgetPassword(phoneNumber)
            if (passWord.isNotEmpty()) {
                DialogUtils.showMessageDialog("Mật khẩu hiện tại là : $passWord.", this)
            } else {
                DialogUtils.showMessageDialog("Không tìm thấy mật khẩu với sô điện thoại này !", this)
            }
            alertDialog.dismiss()
        }

        mEditTextPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mButtonSend.isEnabled = s.toString() != "" || s.toString().isNotEmpty()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
        dialog.setCancelable(true)
        alertDialog.show()
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        mButtonSignIn.isEnabled = s.toString() != "" || s.toString().isNotEmpty()
    }

    override fun afterTextChanged(s: Editable) {

    }
}

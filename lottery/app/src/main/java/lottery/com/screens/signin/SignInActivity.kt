package lottery.com.screens.signin

import android.app.AlertDialog
import android.app.ProgressDialog
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
import lottery.com.helper.Constants
import lottery.com.helper.Dialog

class SignInActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {


    private val TAG = "SignInActivity"

    private var data: User? = null

    private var mProgressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        mProgressDialog = ProgressDialog(this)
        mProgressDialog?.setMessage("Loading data ...")
        mProgressDialog?.setCancelable(false)
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
            Dialog.MessageDialog.showMessageDialog(" Please, input phone number.", this)
            return false
        }
        if (mEditTextPassword.text.isEmpty() || mEditTextPassword.text == null) {
            Dialog.MessageDialog.showMessageDialog(" Please, input password.", this)
            return false
        }

        return true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mButtonSignIn -> {
                mProgressDialog?.show()
                if (validate()) {
                    when (DBHelper().userLogin(mEditTextPhone.text.toString(), mEditTextPassword.text.toString())) {
                        true -> {
                            if (data != null) {
                                val intent = Intent(this, HomeActivity::class.java)
                                intent.putExtra(Constants.Data.DATA, data)
                                startActivity(intent)
                                finish()
                            } else {
                                val result = DBHelper().getUserByPhone(mEditTextPhone.text.toString())
                                if (result != null) {
                                    val intent = Intent(this, HomeActivity::class.java)
                                    intent.putExtra(Constants.Data.DATA, result)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }
                        false -> {
                            Dialog.MessageDialog.showMessageDialog("Wrong user name or password !", this)
                        }
                    }
                    mProgressDialog?.dismiss()
                }
            }
            R.id.mTextViewForgetPwd ->
                /**
                 * Open dialog
                 */
                createDialog()
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

    private fun createDialog() {
        val dialog = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.forgot_password_dialog, null)
        dialog.setView(view)
        val mButtonClose = view.findViewById(R.id.mButtonClose) as Button
        val mButtonSend = view.findViewById(R.id.mButtonSend) as Button
        val mEditTextPhone = view.findViewById(R.id.mEditTextPhone) as EditText
        val alertDialog = dialog.create()
        mButtonSend.isEnabled = false

        mButtonClose.setOnClickListener { alertDialog.dismiss() }
        mButtonSend.setOnClickListener {
            val phoneNumber = mEditTextPhone.text.toString()
            val passWord = DBHelper().forgetPassword(phoneNumber)
            if (passWord.isNotEmpty()) {
                Dialog.MessageDialog.showMessageDialog("Your pass word is : $passWord", this)
            } else {
                Dialog.MessageDialog.showMessageDialog("Not found pass word with this phone number", this)
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

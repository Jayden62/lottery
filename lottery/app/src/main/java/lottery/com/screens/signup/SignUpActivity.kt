package lottery.com.screens.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_sign_up.*
import lottery.com.R
import lottery.com.database.DBHelper
import lottery.com.model.User
import lottery.com.screens.signin.SignInActivity
import lottery.com.helper.Constants
import lottery.com.helper.Dialog
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
            Dialog.MessageDialog.showMessageDialog(" Please, input name.", this)
            return false
        }
        if (mEditTextPhone.text.isEmpty() || mEditTextPhone.text == null) {
            Dialog.MessageDialog.showMessageDialog(" Please, input phone number.", this)
            return false
        }

        if (mEditTextPhone.text.length > 10 || mEditTextPhone.text.length < 10) {
            Dialog.MessageDialog.showMessageDialog(" phone number is enough 10 characters.", this)
            return false
        }
        if (mEditTextPassword.text.isEmpty() || mEditTextPassword.text == null) {
            Dialog.MessageDialog.showMessageDialog(" Please, input password.", this)
            return false
        }
        if (mEditTextPassword.text.length < 6) {
            Dialog.MessageDialog.showMessageDialog("pass word at least 6 characters.", this)
            return false
        }
        if (mEditTextAddress.text.isEmpty() || mEditTextAddress.text == null) {
            Dialog.MessageDialog.showMessageDialog(" Please, input address.", this)
            return false
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mButtonRegister -> {

                val data = User(
                    mEditTextName.text.toString(),
                    mEditTextPhone.text.toString(),
                    mEditTextPassword.text.toString(),
                    sex!!,
                    mEditTextAddress.text.toString(),
                    UUID.randomUUID().toString()
                )

                if (validate()) {


                    when (DBHelper().registerAccount(data)) {
                        true -> {
                            clearData()
                            val intent = Intent(this, SignInActivity::class.java)
                            intent.putExtra(Constants.Data.DATA, data)
                            startActivity(intent)
                        }
                        false -> {
                            Dialog.MessageDialog.showMessageDialog("Phone number is existed !", this)
                        }
                    }

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

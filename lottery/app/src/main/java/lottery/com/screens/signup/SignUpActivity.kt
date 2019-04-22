package lottery.com.screens.signup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_sign_up.*
import lottery.com.R
import lottery.com.database.DBHelper
import lottery.com.utils.Dialog
import java.sql.Connection

class SignUpActivity : AppCompatActivity(), View.OnClickListener, TextWatcher, RadioGroup.OnCheckedChangeListener {

    private var gender: String? = null

    private var conn: Connection? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mButtonRegister.setOnClickListener(this)
        mEditTextName.addTextChangedListener(this)
        mRadioGroup.setOnCheckedChangeListener(this)

        gender = mRadioMale.text.toString()


    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.mRadioMale -> {
                gender = mRadioMale.text.toString()
            }
            R.id.mRadioFemale -> {
                gender = mRadioFemale.text.toString()
            }
        }
    }

    private fun validate(): Boolean {
        if (mEditTextName.text.isEmpty() || mEditTextName.text == null) {
            Dialog.showMessageDialog(" Please, input name.", this)
            return false
        }
        if (mEditTextPhone.text.isEmpty() || mEditTextPhone.text == null) {
            Dialog.showMessageDialog(" Please, input phone number.", this)
            return false
        }

        if (mEditTextPhone.text.length > 10 || mEditTextPhone.text.length < 10) {
            Dialog.showMessageDialog(" phone number is enough 10 characters.", this)
            return false
        }
        if (mEditTextPassword.text.isEmpty() || mEditTextPassword.text == null) {
            Dialog.showMessageDialog(" Please, input password.", this)
            return false
        }
        if (mEditTextPassword.text.length < 6) {
            Dialog.showMessageDialog("pass word at least 6 characters.", this)
            return false
        }
        if (mEditTextAddress.text.isEmpty() || mEditTextAddress.text == null) {
            Dialog.showMessageDialog(" Please, input address.", this)
            return false
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mButtonRegister -> {
                if (validate()) {
                    val isChecked = DBHelper().checkUserLogin(
                        mEditTextName.text.toString(),
                        mEditTextPhone.text.toString(),
                        mEditTextPassword.text.toString(),
                        gender!!,
                        mEditTextAddress.text.toString(),
                        "accessToken", this
                    )
                    when (isChecked) {
                        true -> {
                            Dialog.showMessageDialog("Succeed.", this)
                        }
                        false -> {
                            return
                        }
                    }
                }

            }
        }

    }


    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        mButtonRegister.isEnabled = s.toString() != "" || s.toString().isNotEmpty()

    }

}

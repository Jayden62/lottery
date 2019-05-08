package lottery.com.helper

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AlertDialog

object Dialog {

    object MessageDialog {
        fun showMessageDialog(message: String, mContext: Context) {
            val builder = AlertDialog.Builder(mContext)
            builder.setMessage(message)
            builder.setPositiveButton(
                "Oke"
            ) { dialog, which -> dialog.dismiss() }
            builder.setCancelable(false)
            builder.show()
        }
    }

}
package lottery.com.helper

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.widget.Toast

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

        fun showToastMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
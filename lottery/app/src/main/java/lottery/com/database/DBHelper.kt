package lottery.com.database

import android.content.Context
import android.os.StrictMode
import android.util.Log
import lottery.com.utils.Constants
import lottery.com.utils.DataHelper
import lottery.com.utils.Dialog
import java.lang.Exception
import java.sql.*

class DBHelper {

    private var TAG = "DBHelper"

    private var conn: Connection? = null

    private var item: String? = null

    @Throws(ClassNotFoundException::class, SQLException::class)
    private fun getConnection(driver: String, url: String, username: String, password: String): Connection? {
        Class.forName(driver)
        return DriverManager.getConnection(url, username, password)
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun createConnection(): Connection? {
        return getConnection(
            Constants.Config.driverName,
            Constants.Config.url,
            Constants.Config.userName,
            Constants.Config.password
        )
    }

    private fun initPermission() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
    }

    fun registerAccount(
        name: String,
        phoneNumber: String,
        passWord: String,
        gender: String,
        address: String,
        accessToken: String,
        context: Context
    ): Boolean {
        try {
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query = "SELECT CELLPHONE FROM ACCOUNT_DT"
            val sqlQuery =
                "INSERT INTO ACCOUNT_DT(NAME_ID, CELLPHONE, PASSWORD_ID, SEX, ADDRESS,ACCESS_TOKEN, ROLE_ID) VALUES ('$name','$phoneNumber','$passWord','$gender','$address','$accessToken',0)"
            val statement = conn?.createStatement()
            var rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                item = DataHelper.initText(rs.getString("CELLPHONE"))
                if (phoneNumber == item) {
                    Dialog.showMessageDialog("phone number is existed.", context)
                } else {
                    rs = statement?.executeQuery(sqlQuery)
                }
            }
            return true
            conn?.close()
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return false
    }

    fun forgetPassword(phoneNumber: String): String {
        try {
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query = "select password_id from account_dt where cellphone = '$phoneNumber'"
            val statement = conn?.createStatement()
            var rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                item = DataHelper.initText(rs.getString("password_id"))
            }
            return item!!
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return ""
    }

    fun userLogin(phoneNumber: String, passWord: String): Boolean {
        try {
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query =
                "select cellphone, password_id from account_dt where cellphone = '$phoneNumber' and password_id = '$passWord'"
            val statement = conn?.createStatement()
            statement?.executeQuery(query)
            return true
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return false
    }

}
package lottery.com.database

import android.content.Context
import android.os.StrictMode
import android.util.Log
import lottery.com.utils.Constants
import lottery.com.utils.DataHelper
import lottery.com.utils.Dialog

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class DBHelper {

    private var TAG = "DBHelper"

    private var conn: Connection? = null

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

    private fun registerAccount(
        name: String,
        phoneNumber: String,
        passWord: String,
        gender: String,
        address: String,
        accessToken: String
    ): Boolean {
        try {
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val sqlQuery =
                "INSERT INTO ACCOUNT_DT(NAME_ID, CELLPHONE, PASSWORD_ID, SEX, ADDRESS,ACCESS_TOKEN, ROLE_ID) VALUES ('$name','$phoneNumber','$passWord','$gender','$address','$accessToken',0)"
            val statement = conn?.createStatement()
            statement?.execute(sqlQuery)
            return true
            conn?.close()
        } catch (e: java.lang.Exception) {
            Log.d(TAG, e.message)
        }
        return false
    }

    fun checkUserLogin(
        name: String,
        phoneNumber: String,
        passWord: String,
        gender: String,
        address: String,
        accessToken: String, context: Context
    ): Boolean {
        var item = checkPhoneNumberExist(phoneNumber)
        return if (item != null) {
            item = ""
            Dialog.showMessageDialog("phone number is existed.", context)
            false
        } else {
            registerAccount(name, phoneNumber, passWord, gender, address, accessToken)
            true
        }
    }

    private fun checkPhoneNumberExist(phoneNumber: String): String {
        try {
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val sqlQuery =
                "SELECT CELLPHONE FROM ACCOUNT_DT WHERE CELLPHONE = (SELECT CELLPHONE FROM ACCOUNT_DT WHERE CELLPHONE = '$phoneNumber')"
            val statement = conn?.createStatement()
            statement?.execute(sqlQuery)
            return phoneNumber
            conn?.close()
        } catch (e: java.lang.Exception) {
            Log.d(TAG, e.message)
        }
        return ""
    }

    private fun getAllPhoneNumber(): MutableList<String>? {
        try {
            val phoneList: MutableList<String> = mutableListOf()
            var item: String? = null
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val sqlQuery = "SELECT CELLPHONE FROM ACCOUNT_DT"
            val statement = conn?.createStatement()
            val rs: ResultSet? = statement?.executeQuery(sqlQuery)
            while (rs?.next()!!) {
                item = DataHelper.initText(rs.getString("CELLPHONE"))
                phoneList.add(item)
            }
            return phoneList
            conn?.close()
        } catch (e: java.lang.Exception) {
            Log.d(TAG, e.message)
        }
        return null
    }
}
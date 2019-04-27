package lottery.com.database

import android.os.StrictMode
import android.util.Log
import lottery.com.model.User
import lottery.com.utils.Constants
import lottery.com.utils.DataHelper
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

    fun registerAccount(user: User): Boolean {
        try {
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val sqlQuery =
                "INSERT INTO ACCOUNT_DT(NAME_ID, CELLPHONE, PASSWORD_ID, SEX, ADDRESS,ACCESS_TOKEN) VALUES ('${user.name}','${user.phoneNumber}','${user.passWord}','${user.sex}','${user.address}','${user.accessToken}')"
            val statement = conn?.createStatement()
            statement?.execute(sqlQuery)
            return true
            conn?.close()
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return false
    }

    fun getUserByPhone(phoneNumber: String): User? {
        try {
            initPermission()
            this.conn = createConnection()
            var data: User? = null
            Log.d(TAG, "Connected")
            val sqlQuery =
                "SELECT * FROM ACCOUNT_DT WHERE CELLPHONE = '$phoneNumber'"
            val statement = conn?.createStatement()
            var rs: ResultSet? = statement?.executeQuery(sqlQuery)
            while (rs!!.next()) {
                val name = rs.getString("name_id")
                val phone = rs.getString("cellphone")
                val pwd = rs.getString("password_id")
                val sex = rs.getString("sex")
                val address = rs.getString("address")
                val token = rs.getString("access_token")

                data = User(name, phone, pwd, sex, address, token)
            }

            return data

            conn?.close()
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return null
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

    fun checkOldPassword(phone: String): Boolean {
        try {
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query =
                "select password_id from account_dt where cellphone = '$phone' "
            val statement = conn?.createStatement()
            statement?.executeQuery(query)
            return true
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return false
    }
}
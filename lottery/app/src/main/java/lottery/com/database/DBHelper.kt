package lottery.com.database

import android.annotation.SuppressLint
import android.os.StrictMode
import android.util.Log
import lottery.com.model.User
import lottery.com.helper.Constants
import lottery.com.helper.DataHelper
import lottery.com.model.TypeService
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

    @SuppressLint("ObsoleteSdkInt")
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
            val query = "select cellphone from account_dt"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                item = rs?.getString("cellphone")
                return if (item == user.phoneNumber) {
                    false
                } else {
                    val pmtm = conn?.prepareStatement(sqlQuery)
                    pmtm?.execute()
                    true
                }
            }
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
            var phone: String? = null
            var password: String? = null
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query =
                "select cellphone, password_id from account_dt where cellphone = '$phoneNumber' and password_id = '$passWord'"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                phone = rs.getString("cellphone")
                password = rs.getString("password_id")
            }

            if (phone.equals(phoneNumber) && password.equals(passWord)) {
                return true
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return false
    }

    fun updatePassword(phone: String, password: String, newPassword: String): Boolean {
        try {
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query =
                "update account_dt\n" +
                        "set password_id = '$newPassword'\n" +
                        "where cellphone = '$phone' and password_id = '$password'"
            val statement = conn?.createStatement()
            statement?.executeQuery(query)
            return true
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return false
    }

    fun updateProfile(phone: String, name: String, address: String): Boolean {
        try {
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query =
                "update account_dt\n" +
                        "set name_id= '$name', address = '$address'\n" +
                        "where cellphone = '$phone'"
            val statement = conn?.createStatement()
            statement?.executeQuery(query)
            return true
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return false
    }

    fun getTypeService(): MutableList<TypeService>? {
        try {
            val types: MutableList<TypeService> = mutableListOf()
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query = "select * from type_service"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                val id = rs.getInt("type_service_id")
                val name = rs.getString("type_service_name")
                val image = rs.getString("type_service_img")
                val typeService = TypeService(id, name, image)
                types.add(typeService)
            }
            return types
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return null
    }
}
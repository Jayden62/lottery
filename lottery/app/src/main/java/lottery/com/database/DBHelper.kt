package lottery.com.database

import android.annotation.SuppressLint
import android.os.StrictMode
import android.util.Log
import lottery.com.utils.Constants
import lottery.com.utils.DataHelper
import lottery.com.utils.DateHelper
import lottery.com.model.*
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
                item = rs.getString("cellphone")
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
            val rs: ResultSet? = statement?.executeQuery(sqlQuery)
            while (rs!!.next()) {
                val name = rs.getString("name_id")
                val phone = rs.getString("cellphone")
                val pwd = rs.getString("password_id")
                val sex = rs.getString("sex")
                val address = rs.getString("address")
                val token = rs.getString("access_token")

                data = User(name, phone, pwd, sex, address, token)
            }
            conn?.close()
            return data
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

    fun getTypeServices(): MutableList<TypeService>? {
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

    fun getServices(): MutableList<Service>? {
        try {
            val services: MutableList<Service> = mutableListOf()
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query = "select * from service"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                val id = rs.getInt("service_id")
                val name = rs.getString("service_name")
                val price = rs.getInt("price")
                val time = rs.getInt("time_todo")
                val detail = rs.getString("detail_service")
                val typeId = rs.getInt("type_service_id")
                val item = Service(id, name, price, time, detail, typeId)
                services.add(item)
            }
            return services
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return null
    }

    fun getServicePromotion(): Int {
        var id = 0
        try {
            initPermission()
            this.conn = createConnection()
            val query = "select service_id  from service where service_id = (select service_id from news )"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                id = rs.getInt("service_id")
            }
            return id
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return -1
    }

    fun getNews(): MutableList<News>? {
        try {
            val newsList: MutableList<News> = mutableListOf()
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query = "select * from news"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                val id = rs.getInt("news_id")
                val name = rs.getString("news_name")
                val image = rs.getString("news_img")
                val detail = rs.getString("details_news")
                val startDate = rs.getDate("start_day")
                val endDate = rs.getDate("end_day")
                val serviceId = rs.getInt("service_id")
                val item = News(
                    id,
                    name,
                    image,
                    detail,
                    DateHelper.parseDate(startDate, Constants.Date.FORMAT_DD_MM_YYYY_HYPEN),
                    DateHelper.parseDate(endDate, Constants.Date.FORMAT_DD_MM_YYYY_HYPEN),
                    serviceId
                )
                newsList.add(item)
            }
            return newsList
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return null
    }

    fun getMainTimesFrame(): MutableList<MainTimeFrame>? {
        try {
            val list: MutableList<MainTimeFrame> = mutableListOf()
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query = "SELECT * FROM TIMEFRAME WHERE TIMEFRAME_ID BETWEEN 14 AND 16"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                val id = rs.getInt("timeframe_id")
                val detail = rs.getString("details_timeframe")
                val state = rs.getInt("state")
                val item = MainTimeFrame(id, detail, state)
                list.add(item)
            }
            return list
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return null
    }

    fun getTimesFrameActive(timeId: Int, strDate: String?): MutableList<MainTimeFrame>? {
        try {
            val list: MutableList<MainTimeFrame> = mutableListOf()
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query = "SELECT * FROM TIMEFRAME WHERE TIMEFRAME_ID IN \n" +
                    "( SELECT TIMEFRAME_ID FROM TIMEFRAME WHERE STATE = $timeId\n" +
                    "  MINUS\n" +
                    "  SELECT TIMEFRAME_ID FROM SCHEDULE WHERE SCH_DAY = '$strDate') ORDER BY TIMEFRAME_ID ASC"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                val id = rs.getInt("timeframe_id")
                val detail = rs.getString("details_timeframe")
                val state = rs.getInt("state")
                val item = MainTimeFrame(id, detail, state)
                list.add(item)
            }
            return list
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return null
    }

    fun getPassword(passWord: String?, phone: String?): User? {
        try {
            initPermission()
            this.conn = createConnection()
            var data: User? = null
            val query = "select * from account_dt where password_id = '$passWord' and cellphone = '$phone'"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                val name = rs.getString("name_id")
                val phone = rs.getString("cellphone")
                val pwd = rs.getString("password_id")
                val sex = rs.getString("sex")
                val address = rs.getString("address")
                val token = rs.getString("access_token")

                data = User(name, phone, pwd, sex, address, token)
            }
            return data
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return null
    }

    fun getUserIdByphone(phone: String): Int {
        try {
            initPermission()
            this.conn = createConnection()
            var id: Int = 0
            val query = "select account_id from account_dt where cellphone = '$phone'"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                id = rs.getInt("account_id")
            }
            return id
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return -1
    }

    fun createSchedule(
        userId: Int,
        frameId: Int,
        day: String,
        date: String,
        qrCode: String,
        name: String
    ): Boolean {
        try {
            initPermission()
            this.conn = createConnection()
            val query =
                "INSERT INTO SCHEDULE (account_id, TIMEFRAME_ID, SCH_DAYOFDATE, SCH_DAY, QRCODE, NAME_SER) VALUES ($userId,$frameId,'$day','$date','$qrCode', '$name')"
            val statement = conn?.createStatement()
            statement?.execute(query)
            return true
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return false
    }
}
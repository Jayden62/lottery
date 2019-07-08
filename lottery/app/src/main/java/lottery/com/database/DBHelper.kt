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
import kotlin.math.sqrt

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

    private fun checkPhoneNumber(): MutableList<String>? {
        try {
            initPermission()
            this.conn = createConnection()
            val list: MutableList<String>? = mutableListOf()
            var mPhone: String? = null
            val query = "select cellphone from user_dt"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                mPhone = rs.getString("cellphone")
                list?.add(mPhone)
            }
            return list
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return null
    }

    /**
     * Hàm này để kiểm tra user có trong database.
     */
    fun checkUserId(phone: String?, user: User): Boolean {
        try {
            initPermission()
            this.conn = createConnection()
            var userId = 0
            val query = "select user_id from user_dt where cellphone = '$phone'"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                userId = rs.getInt("user_id")
            }
            return if (userId != 0) {
                false
            } else {
                registerAccount(user)
                true
            }

        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return false
    }

    private fun registerAccount(user: User) {
        try {
            initPermission()
            this.conn = createConnection()
            val statement = conn?.createStatement()
            val query =
                "INSERT INTO USER_DT(name_user, CELLPHONE, PASSWORD_user, SEX, ADDRESS,ACCESS_TOKEN) VALUES ('${user.name}','${user.phoneNumber}','${user.passWord}','${user.sex}','${user.address}','${user.accessToken}')"
            statement?.execute(query)
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
    }

    fun getUserByPhone(phoneNumber: String): User? {
        try {
            initPermission()
            this.conn = createConnection()
            var data: User? = null
            Log.d(TAG, "Connected")
            val sqlQuery =
                "SELECT * FROM user_DT WHERE CELLPHONE = '$phoneNumber'"
            val statement = conn?.createStatement()
            val rs: ResultSet? = statement?.executeQuery(sqlQuery)
            while (rs!!.next()) {
                val name = rs.getString("name_user")
                val phone = rs.getString("cellphone")
                val pwd = rs.getString("password_user")
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
            val query = "select password_user from user_dt where cellphone = '$phoneNumber'"
            val statement = conn?.createStatement()
            var rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                item = DataHelper.initText(rs.getString("password_user"))
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
                "select cellphone, password_user from user_dt where cellphone = '$phoneNumber' and password_user = '$passWord'"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                phone = rs.getString("cellphone")
                password = rs.getString("password_user")
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
                "update user_dt\n" +
                        "set password_user = '$newPassword'\n" +
                        "where cellphone = '$phone' and password_user = '$password'"
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
                "update user_dt\n" +
                        "set name_user= '$name', address = '$address'\n" +
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
                    "(\n" +
                    "  SELECT TIMEFRAME_ID FROM TIMEFRAME WHERE STATE IN (SELECT TIMEFRAME_ID FROM STAFF_SCH WHERE TIMEFRAME_ID = $timeId \n" +
                    "  AND DMY_ID = (select * from(SELECT DMY_ID FROM DMY WHERE DMY_DATE like ('%$strDate') order by dmy_id asc)where rownum =1))\n" +
                    "  MINUS\n" +
                    "  SELECT TIMEFRAME_ID FROM SCHEDULE WHERE SCH_DATE like ('%$strDate')\n" +
                    ")\n" +
                    "ORDER BY TIMEFRAME_ID ASC"
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
            val query = "select * from user_dt where password_user = '$passWord' and cellphone = '$phone'"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                val name = rs.getString("name_user")
                val phone = rs.getString("cellphone")
                val pwd = rs.getString("password_user")
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

    fun getUserIdByPhone(phone: String): Int {
        try {
            initPermission()
            this.conn = createConnection()
            var id: Int = 0
            val query = "select user_id from user_dt where cellphone = '$phone'"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                id = rs.getInt("user_id")
            }
            return id
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return -1
    }

    fun createDate(
        userId: Int,
        staffId: Int,
        timeId: Int,
        day: String,
        date: String,
        qrCode: String
    ): Boolean {
        try {
            initPermission()
            this.conn = createConnection()
            val query =
                "INSERT INTO SCHEDULE (user_id, staff_id,timeframe_id, SCH_DATE, SCH_DAY, QRCODE) VALUES ($userId,$staffId,$timeId,'$day','$date','$qrCode')"
            val statement = conn?.createStatement()
            statement?.execute(query)
            return true
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return false
    }

    fun getScheduleIdByUserId(userId: Int, date: String): Int? {
        try {
            initPermission()
            this.conn = createConnection()
            var scheduleId: Int? = 0
            val query =
                "SELECT SCH_ID FROM SCHEDULE WHERE user_ID = '$userId'  AND SCH_DATE = '$date'"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                scheduleId = rs.getInt("sch_id")
            }
            return scheduleId
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return -1
    }

    fun getStaffId(frameId: Int, strDate: String): Int {
        try {
            initPermission()
            this.conn = createConnection()
            var id: Int? = 0
            val query = "select staff_id from staff_sch where timeframe_id = $frameId" +
                    "and dmy_id = (select dmy_id from dmy where dmy_date = '$strDate')"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs!!.next()) {
                id = rs.getInt("staff_id")
            }
            return id!!
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return -1
    }

    fun createSchedule(id: Int, serviceId: Int) {
        try {
            initPermission()
            this.conn = createConnection()
            val query = "INSERT INTO DETAILS_SCH (SCH_ID, SERVICE_ID) VALUES ($id,$serviceId)"
            val statement = conn?.createStatement()
            statement?.execute(query)
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
    }

    fun getBooked(userId: Int): MutableList<Booked>? {
        try {
            val list: MutableList<Booked> = mutableListOf()
            initPermission()
            this.conn = createConnection()
            Log.d(TAG, "Connected")
            val query =
                "SELECT DETAILS_TIMEFRAME, SCH_DATE, SERVICE_NAME FROM SCHEDULE A, TIMEFRAME B, SERVICE C, DETAILS_SCH D WHERE A.TIMEFRAME_ID = B.TIMEFRAME_ID \n" +
                        "AND A.SCH_ID = D.SCH_ID AND C.SERVICE_ID = D.SERVICE_ID AND USER_ID = $userId"
            val statement = conn?.createStatement()
            val rs = statement?.executeQuery(query)
            while (rs?.next()!!) {
                val detailsTimeFrame = rs.getString("DETAILS_TIMEFRAME")
                val schDate = rs.getString("SCH_DATE")
                val serviceNName = rs.getString("SERVICE_NAME")
                val item = Booked(detailsTimeFrame, schDate, serviceNName)
                list.add(item)
            }
            return list
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        return null
    }
}
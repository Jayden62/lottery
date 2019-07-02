package lottery.com.utils

object Constants {
    object Config {
        private const val serverName = "172.16.0.228"
        private const val portNumber = "1521"
        private const val db = "db12c"
        const val driverName = "oracle.jdbc.driver.OracleDriver"
        const val url = "jdbc:oracle:thin:@$serverName:$portNumber:$db"
        const val userName = "SYSTEM"
        const val password = "vycnnah"
    }

    object Data {
        const val DATA = "DATA"
        const val MODEL = "MODEL"
        const val DATE = "DATE"
        const val DAY = "DAY"
        const val MY_MODEL = "MY MODEL"
        const val USER = "USER"
        const val MAINFRAME = "MAIN_FRAME"
    }


    object Date {
        const val FORMAT_DD_MM_YYYY_HYPEN = "dd-MM-yyyy"
    }

    object Room {
        const val db_name = "dental"
    }
}
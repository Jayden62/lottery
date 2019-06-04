package lottery.com.utils

object Constants {
    object Config {
        private const val serverName = "10.7.2.18"
        private const val portNumber = "1521"
        private const val db = "db12c"
        const val driverName = "oracle.jdbc.driver.OracleDriver"
        const val url = "jdbc:oracle:thin:@$serverName:$portNumber:$db"
        const val userName = "SYSTEM"
        const val password = "vycnnah"
    }

    object Data {
        const val DATA = "DATA"
    }

    object Date {
        const val FORMAT_DD_MM_YYYY_HYPEN = "dd-MM-yyyy"
    }
}
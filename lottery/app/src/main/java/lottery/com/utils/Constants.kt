package lottery.com.utils

object Constants {
    object Config {
        private const val serverName = "192.168.1.36"
        private const val portNumber = "1521"
        private const val db = "db12c"
        const val driverName = "oracle.jdbc.driver.OracleDriver"
        const val url = "jdbc:oracle:thin:@$serverName:$portNumber:$db"
        const val userName = "SYSTEM"
        const val password = "vycnnah"
    }
}
package lottery.com.helper

object Constants {
    object Config {
        private const val serverName = "172.16.185.125"
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
}
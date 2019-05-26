package lottery.com.utils

object DataHelper {

    fun initText(value: String): String {
        if (value == null) {
            return ""
        }
        if (value.isEmpty() || value.trim().isEmpty()) {
            return ""
        }
        return value
    }

}
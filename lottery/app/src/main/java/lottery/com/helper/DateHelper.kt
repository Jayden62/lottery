package lottery.com.helper

import java.text.SimpleDateFormat
import java.util.*


object DateHelper {
    fun parseDate(date: Date?, pattern: String): String {
        val formatDate = SimpleDateFormat(pattern)
        return formatDate.format(date)
    }


}
import java.text.SimpleDateFormat
import java.util.*

fun formatDateToDDMMYYYY(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    val date = inputFormat.parse(dateString)!!
    return outputFormat.format(date)
}

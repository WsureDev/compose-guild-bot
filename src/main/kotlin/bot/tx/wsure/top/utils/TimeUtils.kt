package bot.tx.wsure.top.utils

import bot.tx.wsure.top.utils.TimeUtils.WB_FORMATTER
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

object TimeUtils {
    val DAY_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")
    val WB_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH)// XXX yyyy

    fun todayString(): String {
        return LocalDateTime.now().format(DAY_FORMATTER)
    }

    fun Long.toLocalDateTime():LocalDateTime{
        val instant = if(this.toString().length > 10) Instant.ofEpochMilli(this)
        else Instant.ofEpochSecond(this)
        return  LocalDateTime.ofInstant(
            instant,
            TimeZone.getDefault().toZoneId()
        )
    }
    fun LocalDateTime.toEpochMilli():Long{
        return  this.toInstant(OffsetDateTime.now().offset).toEpochMilli()
    }

}

object WBTimeSerializer : KSerializer<ZonedDateTime> {
    override fun deserialize(decoder: Decoder): ZonedDateTime {
        return ZonedDateTime.parse(decoder.decodeString(), WB_FORMATTER)
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("WBTimeSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ZonedDateTime) {
        encoder.encodeString(value.format(WB_FORMATTER))
    }
}
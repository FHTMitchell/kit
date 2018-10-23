package kit.kdate

import java.time.LocalDate

const val SECONDS_IN_MINUTE = 60
const val MINUTES_IN_HOUR = 60
const val HOURS_IN_DAY = 24

const val MILLISECONDS_IN_SECOND = 1000L
const val MICROSECONDS_IN_SECOND = 1000L * MILLISECONDS_IN_SECOND
const val NANOSECONDS_IN_SECOND = 1000L * MICROSECONDS_IN_SECOND

const val SECONDS_IN_HOUR  = SECONDS_IN_MINUTE * MINUTES_IN_HOUR
const val SECONDS_IN_DAY = SECONDS_IN_MINUTE * MINUTES_IN_HOUR
const val MINUTES_IN_DAY = MINUTES_IN_HOUR * HOURS_IN_DAY


class DateTimeException(msg: String = ""): Exception(msg)


private fun <T: Comparable<T>> checkInRange(name: String, value: T, range: ClosedRange<T>) {
    if (value !in range)
        throw DateTimeException(
                "$name must be between ${range.start} and ${range.endInclusive} inclusive, not $value"
        )
}


enum class TimeUnit(val inSeconds: Double) {
    NANOSECOND(1e-9),
    MICROSECOND(1e-6),
    MILLISECOND(1e-3),
    SECOND(1.0),
    MINUTE(SECONDS_IN_MINUTE.toDouble()),
    HOUR(SECONDS_IN_HOUR.toDouble()),
    DAY(SECONDS_IN_DAY.toDouble());
}

enum class Month(val value: Int, private val daysInNotLeapYear: Int, private val daysInLeapYear: Int? = null) {
    JANUARY(1, 31),
    FEBRUARY(2, 28, 29),
    MARCH(3, 31),
    APRIL(4, 30),
    MAY(5, 31),
    JUNE(6, 30),
    JULY(7, 31),
    AUGUST(8, 31),
    SEPTEMBER(9, 30),
    OCTOBER(10, 31),
    NOVEMBER(11, 30),
    DECEMBER(12, 31);

    fun getNumberOfDays(isLeapYear: Boolean): Int {
        return if (!isLeapYear) {
            this.daysInNotLeapYear
        } else {
            this.daysInLeapYear ?: this.daysInNotLeapYear
        }
    }
}


interface TimeZone {
    val name: String
    val offsetHours: Int
    val offsetMinutes: Int
}


interface Date {
    val year: Int
    val month: Month
    val dayOfMonth: Int

    val midnight: DateTime
    val midday: DateTime
    val tomorrow: Date
    val yesterday: Date

    operator fun plus(other: TimeDelta): Date

    operator fun minus(other: TimeDelta): Date


}


interface Time {
    val hour: Int
    val minute: Int
    val second: Int
    val nanosecond: Int
    val tz: TimeZone

    operator fun plus(other: TimeDelta): Time

    operator fun minus(other: TimeDelta): Time
}

interface TimeDelta {
    val days: Int
    val seconds: Int
    val nanoseconds: Int

    val totalSeconds: Double
    val totalNanoSeconds: Long

    operator fun unaryMinus(): TimeDelta
    operator fun unaryPlus() = this
}



interface DateTime: Date, Time {

    override operator fun plus(other: TimeDelta): DateTime

    override operator fun minus(other: TimeDelta): DateTime

    val date: Date
    val time: Time
}


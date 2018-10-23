@file:JvmName("OptionUtils")

package kit.kboxes.option

internal const val NULL_HASH = 0x0000

class NoneException(msg: String): Exception(msg)

sealed class Option<out T> {

    /** For use in java. In kotlin, use `is Some` */
    fun isSome(): Boolean = this is Some

    /** For use in java. In kotlin, use `is None` or `== None`*/
    fun isNone(): Boolean = this === None

}

// implementations

class Some<out T>(val value: T): Option<T>() {

    override fun toString() = "Some($value)"

    override fun hashCode(): Int = this.value?.hashCode() ?: NULL_HASH

    override fun equals(other: Any?): Boolean = this.value == other
}

object None: Option<Nothing>() {

    override fun toString() = "None"

    // hashCode and equals for None unnecessary
}

// extensions

fun <T> Option<T>.expect(msg: Option<String> = None): T = when (this) {
    is Some -> this.value
    None -> throw NoneException(msg.getOrDefault(""))
}

fun <T> Option<T>.getOrDefault(default: T): T = when (this) {
    is Some -> this.value
    None -> default
}

fun <T, R> Option<T>.map(func: (T) -> R): Option<R> = when (this) {
    is Some -> Some(func(this.value))
    None -> None
}

fun <T> Option<T>.filter(predicate: (T) -> Boolean): Option<T> = when (this) {
    is Some -> if (predicate(this.value)) this else None
    None -> None
}

infix fun <T> Option<T>.or(other: Option<T>): Option<T> = when (this) {
    is Some -> this
    None -> other
}

infix fun <T> Option<T>.and(other: Option<T>): Option<T> = when (this) {
    is Some -> other
    None -> this
}

@file:JvmName("ResultUtils")

package kit.kboxes.option

sealed class Result<out T, out E: Exception> {

    /** For use in java. In kotlin, use `is Ok` */
    fun isOk() = this is Ok

    /** For use in java. In kotlin, use `is Err` */
    fun isErr() = this is Err

}

// implementations

class Ok<out T>(val value: T): Result<T, Nothing>() {

    override fun toString() = "Ok($value)"

    override fun hashCode() = this.value?.hashCode() ?: NULL_HASH

    override fun equals(other: Any?) =
        if (this === other)
            true
        else when (other) {
            is Ok<*> -> this.value == other.value
            else -> false
        }
}

class Err<out E: Exception>(val error: E): Result<Nothing, E>() {

    override fun toString() = "Err($error)"

    override fun hashCode() = this.error.hashCode()   // error cannot be null

    override fun equals(other: Any?) =
            if (this === other)
                true
            else when (other) {
                is Err<*> -> this.error == other.error
                else -> false
            }
}

// extensions

fun <T, E: Exception> Result<T, E>.ok(): Option<T> = when (this) {
    is Ok -> Some(this.value)
    is Err -> None
}

fun <T, E: Exception> Result<T, E>.err(): Option<E> = when (this) {
    is Ok -> None
    is Err -> Some(this.error)
}


fun <T, E: Exception> Result<T, E>.expect(): T = when (this) {
    is Ok -> this.value
    is Err -> throw this.error
}

fun <T, E: Exception> Result<T, E>.getOrDefault(default: T): T = when (this) {
    is Ok -> this.value
    is Err -> default
}

fun <T, E: Exception, R> Result<T, E>.map(func: (T) -> R): Result<R, E> = when (this) {
    is Ok -> Ok(func(this.value))
    is Err -> Err(this.error)
}

infix fun <T, E: Exception> Result<T, E>.or(other: Result<T, E>): Result<T, E> = when (this) {
    is Ok -> this
    is Err -> other
}

infix fun <T, E: Exception> Result<T, E>.and(other: Result<T, E>): Result<T, E> = when (this) {
    is Ok -> other
    is Err -> this
}


package kit.kiter

import kit.kboxes.multisets.Counter
import kotlin.coroutines.experimental.buildSequence

// data classes
data class Mapped<T, R>(val input: T, val result: R) {
    fun toPair() = this.input to this.result
}

// properties

/**
 * Shortcut for asSequence
 */
val <T> Iterable<T>.seq get() = this.asSequence()


// functions

/**
 * Create a sequence repeating this element n times (or infinite if n is null)
 */
fun <T> T.repeat(times: Int? = null): Sequence<T> = buildSequence {
    for (i in countFrom()) {
        if (i == times)
            break
        yield(this@repeat)
    }
}

/**
 * Create an infinite sequence of integers, starting at start and increasing by step
 *
 * countFrom() => 0 1 2 3 4 5 ...
 * countFrom(1) => 1 2 3 4 5 6 ...
 * countFrom(6, 3) => 6 9 12 15 ...
 */
fun countFrom(start: Int = 0, step: Int = 1) = generateSequence(start) { it + step }

/**
 * Create an infinite sequence that cycles through this iterable
 *
 * asList(1, 2, 3).cycle() => 1 2 3 1 2 3 1 2 3 ...
 */
fun <T> Iterable<T>.cycle(): Sequence<T> = this.toList().repeat().flatten()



/**
 * A map which retains the input value
 *
 * Returns a sequence of Mapped values of the form: `input --> (input, f(input)) --> (input, result)`
 */
fun <T, R> Iterable<T>.mapWith(f: (T) -> R) = this.asSequence().mapWith(f)

/**
 * A map which retains the input value
 *
 * Returns a sequence of Mapped values of the form: `input --> (input, f(input)) --> (input, result)`
 */
fun <T, R> Sequence<T>.mapWith(f: (T) -> R): Sequence<Mapped<T, R>> = buildSequence {
    for (item in this@mapWith) {
        yield(Mapped(item, f(item)))
    }
}


/**
 * The median element of the collection.
 *
 * @return a [List] of one or two elements depending on whether the collection
 * has an even length or not.
 */
fun <T : Comparable<T>> Collection<T>.median(): List<T> {
    val vector: List<T> = this.sorted()
    if (this.size % 2 == 0) {
        return listOf(vector[this.size / 2])
    }
    return listOf(vector[this.size / 2], vector[this.size / 2 + 1])
}

/**
 * The mode (most common values) of this collection.
 *
 * @return a [Set] of the most common elements (can be any size -- empty if iterable is empty)
 */
fun <T> Iterable<T>.mode(): Set<T> {
    val mostCommon = Counter(this).mostCommon()

    if (mostCommon.isEmpty()) return setOf()

    for ((index, count) in mostCommon.withIndex()) {
        if (count.count != mostCommon[0].count) {
            return mostCommon.asSequence().take(index).map { it.element }.toSet()
        }
    }

    return mostCommon.asSequence().map { it.element }.toSet()
}


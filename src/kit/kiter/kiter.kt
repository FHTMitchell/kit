package kit.kiter

import kotlin.coroutines.experimental.buildSequence

/**
 * Create a sequence repeating this element n times (or ininite if n is null)
 */
fun <T> T.repeat(times: Int? = null): Sequence<T> {
    val obj = this
    return buildSequence {
        for (i in countFrom()) {
            if (i == times) break
            yield (obj)
        }
    }
}

/**
 * Create an infinite sequence of integers, starting at start and increasing by step
 *
 * countFrom() => 0 1 2 3 4 5 ...
 * countFrom(1) => 1 2 3 4 5 6 ...
 * countFrom(6, 3) => 6 9 12 15 ...
 */
fun countFrom(start: Int = 0, step: Int = 1) = generateSequence (start) { it + step }

/**
 * Create an infinite sequence that cycles through this iterable
 *
 * asList(1, 2, 3).cycle() => 1 2 3 1 2 3 1 2 3 ...
 */
fun <T> Iterable<T>.cycle(): Sequence<T> {
    return this.toList().repeat().flatten()
}


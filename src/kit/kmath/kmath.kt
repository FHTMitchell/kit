package kit.kmath

import kit.kboxes.multisets.Counter
import kit.kboxes.multisets.MultiSet
import kit.kiter.countFrom
import kotlin.math.sqrt

/**
 * @return `true` if this is a prime number
 */
fun Int.isPrime(): Boolean {

    if (this < 4)
        return this >= 2
    if (this % 2 == 0 || this % 3 == 0)
        return false

    for (i in 5..this.iSqrt() step 6) {
        if (this % i == 0 || this % (i + 2) == 0) {
            return false
        }
    }
    return true
}

/**
 * @return a [List] of primes up to (not including) `lessThan`
 */
fun listOfPrimes(lessThan: Int): List<Int> {
    if (lessThan <= 2) return emptyList()  // empty
    val nums = BooleanArray(lessThan) { true }
    nums[0] = false
    nums[1] = false
    for (num in 2..lessThan.iSqrt()) {
        for (counter in countFrom(num)) {
            val product = counter * num
            if (product >= lessThan) break
            nums[product] = false
        }
    }
    return nums
            .asSequence()
            .withIndex()
            .filter { it.value }
            .map { it.index }
            .toList()
}

/**
 * Find the prime factors of [this]
 * @throws IllegalArgumentException when [this] is less than 1
 * @return a [MultiSet] of `primeFactor: exponent` pairs.
 */
fun Int.factorize(): MultiSet<Int> {

    if (this < 1)
        throw IllegalArgumentException("$this is less that 1")

    val primes = listOfPrimes(this)
    val factors = Counter<Int>()

    var value = this
    var sqrtM: Int
    var valueIsPrime: Boolean

    while (value != 1) {

        if (value in primes) {
            factors.add(value)
            break
        }

        valueIsPrime = true // unless found to be otherwise
        sqrtM = value.iSqrt()
        for (prime in primes.takeWhile { it <= sqrtM }) {
            if (value % prime == 0) {
                value /= prime
                factors.add(prime)
                valueIsPrime = false
                break
            }
        }

        // rare case -- value is prime
        if (valueIsPrime) {
            return factors.also { it.add(value) }
        }
    }

    return factors
}

/**
 * @throws IllegalArgumentException when [this] > 1
 * @return the number of divisions of [this] integer has
 */
fun Int.numFactors(): Int {
    return this.factorize().asSequence().map { it.count + 1 }.product()
}

data class DivModResult<Q, D>(val quotient: Q, val divisor: D)

fun divmod(a: Int, b: Int) = DivModResult(a / b, a % b)
fun divmod(a: Long, b: Long) = DivModResult(a / b, a % b)
fun divmod(a: Double, b: Long): DivModResult<Long, Double> {
    val aAsInt = a.toLong()
    val aFractionPart = a - aAsInt
    val (q, r) = divmod(a.toLong(), b)
    return DivModResult(q, r + aFractionPart)
}
fun divmod(a: Double, b: Int): DivModResult<Int, Double> {
    val (q, d) = divmod(a, b.toLong())
    return DivModResult(q.toInt(), d)
}




//fun Int.allFactors(): List<Int> {
//    todo
//}

/**
 * Calculate the wighted sum
 *
 * @param pairs: An iterable of values and weights
 */
fun weightedSum(pairs: List<Pair<Double, Double>>): Double {
    val numerator = pairs.map { (v, w) -> v * w }.sum()
    val denominator = pairs.map { it.second }.sum()
    return numerator / denominator
}

/**
 * Calculate the weighted sum. `values` and `weights` must be the same length.
 *
 * @param values: The values
 * @param weights: The weights
 */
fun weightedSum(values: List<Double>, weights: List<Double>): Double {
    if (values.size != weights.size)
        throw IllegalArgumentException(
                "keys.size (${values.size}) != weights.size (${weights.size})")
    return weightedSum(values.zip(weights))
}


/**
 * The greatest common divisor of `x` and `y`.
 */
fun gcd(x: Int, y: Int): Int {
    var a = x
    var b = y
    while (b != 0) {
        val tmp = a % b
        a = b
        b = tmp
    }
    return a
}




fun DoubleArray.norm(): Double {
    return this.asSequence().map { it * it }.sum().sqrt()
}

fun DoubleArray.unit(): DoubleArray {
    val norm = this.norm()
    return this.map { it / norm }.toDoubleArray()
}


fun Iterable<Int>.product() = this.reduce(Int::times)
fun Iterable<Long>.product() = this.reduce(Long::times)
fun Iterable<Double>.product() = this.reduce(Double::times)
fun Sequence<Int>.product() = this.reduce(Int::times)
fun Sequence<Long>.product() = this.reduce(Long::times)
fun Sequence<Double>.product() = this.reduce(Double::times)

fun Long.factorial() = if (this > 1) (1L..this).product() else 1
fun Int.factorial() = this.toLong().factorial()

fun Double.sqrt() = sqrt(this)
fun Int.sqrt() = sqrt(this.toDouble())
fun Long.sqrt() = sqrt(this.toDouble())

private fun Double.iSqrt() = sqrt(this).toInt()
private fun Int.iSqrt() = sqrt(this.toDouble()).toInt()

package kit.kmath

import kit.kiter.*
import kotlin.math.*

val Int.isprime: Boolean
    get() {
        if (this < 4)
            return this >= 2
        if (this % 2 == 0 || this % 3 == 0)
            return false

        for (i in 5 .. this.iSqrt() step 6) {
            if (this % i == 0 || this % (i + 2) == 0){
                return false
            }
        }
        return true
}

fun listOfPrimes(lessThan: Int): List<Int> {
    if (lessThan <= 2) return emptyList()  // empty
    val nums = BooleanArray(lessThan) { true }
    nums[0] = false
    nums[1] = false
    for (num in 2 .. lessThan.iSqrt()) {
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

fun Int.factorize(): Map<Int, Int> {

    if (this < 1)
        throw IllegalArgumentException("$this is less that 1")

    val possible_primes = listOfPrimes(this)
    val p_factors = mutableListOf<Int>()

    var m = this
    var sqrtM: Int
    var broke: Boolean

    while (m != 1) {

        if (m in possible_primes) {
            p_factors.add(m)
            break
        }

        broke = false
        sqrtM = m.iSqrt()
        for (prime in possible_primes.takeWhile { it < sqrtM }) {
            if (m % prime == 0) {
                m /= prime
                p_factors.add(prime)
                broke = true
                break
            }
        }

        if (!broke) {
            p_factors.add(m)
            break
        }
    }

    val result = mutableMapOf<Int, Int>()

    for (factor in p_factors) {
        result[factor] = 1 + (result[factor] ?: 0)
    }

    return result

}


fun Int.num_factors(): Int {
    return this.factorize().values.asSequence().map {it + 1}.product()
}

//fun Int.all_factors(): List<Int> {
//    todo
//}

fun weightedSum(itr: List<Pair<Double, Double>>): Double {
    val numerator = itr.map { (v, w) -> v * w } .sum()
    val denominator = itr.map { it.second } .sum()
    return numerator / denominator
}
fun weightedSum(values: List<Double>, weights: List<Double>): Double {
    if (values.size != weights.size)
        throw IllegalArgumentException(
                "values.size (${values.size}) != weights.size (${weights.size})")
    return weightedSum(values.zip(weights))
}


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


fun Collection<Double>.mean() = this.sum()/this.size
fun Collection<Int>.mean() = this.sum()/this.size

fun Collection<Double>.median(): Double {
    val vector = this.sorted()
    if (this.size % 2 == 0){
        return vector[this.size / 2]
    }
    return vector.slice((this.size/2) .. (this.size/2+1)).mean()
}
fun Collection<Int>.median(): Pair<Int, Int> {
    val vector = this.sorted()
    if (this.size % 2 == 0){
        val res = vector[this.size / 2]
        return Pair(res, res)
    }
    return Pair(vector[this.size / 2], vector[this.size / 2 + 1])
}


fun mode(){}

fun Iterable<Int>.product() = this.reduce(Int::times)
fun Iterable<Long>.product() = this.reduce(Long::times)
fun Iterable<Double>.product() = this.reduce(Double::times)
fun Sequence<Int>.product() = this.reduce(Int::times)
fun Sequence<Long>.product() = this.reduce(Long::times)
fun Sequence<Double>.product() = this.reduce(Double::times)

fun Long.factorial() = if (this > 1) (1L..this).product() else 1
fun Int.factorial() = this.toLong().factorial()

private fun Double.iSqrt() = sqrt(this).toInt()
private fun Int.iSqrt() = sqrt(this.toDouble()).toInt()

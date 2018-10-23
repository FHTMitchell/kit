package kit.kboxes

private fun <E> List<E>.normaliseIndex(index: Int): Int? =
        when (index) {
            in 0 until this.size -> index
            in -this.size..-1 -> index + this.size
            else -> null
        }

private fun <E> List<E>.boundIndex(index: Int): Int =
        when {
            index >= this.size -> this.size - 1
            index < 0 -> 0
            else -> index
        }

fun <E> List<E>.getPy(index: Int) = this[this.normaliseIndex(index) ?: index]

fun <E> List<E>.getPy(indices: IntRange): List<E> {
    val start: Int = normaliseIndex(this.boundIndex(indices.start))!!
    val end: Int = normaliseIndex(this.boundIndex(indices.endInclusive))!!
    val step = indices.step

    val result = mutableListOf<E>()
    for (index in start..end step step) {
        result.add(this[index])
    }
    return result
}

fun <E> MutableList<E>.setPy(index: Int, value: E) {
    this[this.normaliseIndex(index) ?: index] = value
}


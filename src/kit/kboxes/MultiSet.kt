package kit.kboxes

interface MultiSet<E> : Iterable<Pair<E, Int>> {

    val keys: Set<E>

    val elements: List<E>

    val size: Int

    val totalSize: Int
        get() = this.elements.size

    fun copy(): MultiSet<E>

    fun addTo(obj: E)
    fun addTo(obj: E, times: Int)

    fun addAllTo(itr: Iterable<E>)

    fun mostCommon(n: Int? = null): List<Pair<E, Int>>

    operator fun get(key: E): Int

    operator fun minus(other: MultiSet<E>): MultiSet<E>

}
